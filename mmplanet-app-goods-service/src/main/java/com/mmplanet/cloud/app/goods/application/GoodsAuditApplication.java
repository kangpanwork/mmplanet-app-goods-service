package com.mmplanet.cloud.app.goods.application;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.util.UUIDHelper;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsDealerGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsAuditListQueryDto;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsDetailRespDto;
import com.mmplanet.cloud.app.goods.domain.event.GoodsAuditPassedEvent;
import com.mmplanet.cloud.app.goods.domain.event.GoodsAuditRejectedEvent;
import com.mmplanet.cloud.app.goods.domain.service.*;
import com.mmplanet.cloud.app.goods.domain.service.impl.GenericServiceImpl;
import com.mmplanet.cloud.app.goods.domain.transfer.GoodsAuditTransfer;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum;
import com.mmplanet.cloud.app.goods.infra.entity.*;
import com.mmplanet.cloud.app.goods.infra.integration.OrderApiClient;
import com.mmplanet.cloud.app.goods.infra.util.HtmlUtils;
import com.mmplanet.cloud.app.goods.infra.util.PageDataUtils;
import com.mmplanet.cloud.app.goods.infra.util.SpringApplicationContentUtils;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditDetailVo;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;
import com.mmplanet.cloud.app.goods.vo.GoodsShopVo;
import com.mmplanet.cloud.app.goods.vo.GoodsVo;
import com.mmplanet.cloud.app.user.enums.UserTypeEnum;
import com.mmplanet.cloud.app.user.vo.AppUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.common.code.BaseCode.FAIL;
import static com.mmplanet.cloud.app.goods.domain.enums.AuditNodeEnum.DEALER;
import static com.mmplanet.cloud.app.goods.domain.enums.AuditNodeEnum.OPS;
import static com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum.*;
import static com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum.TEMPLATE;
import static com.mmplanet.cloud.app.goods.infra.config.GlobalConstant.ASTERISK;
import static com.mmplanet.cloud.app.goods.infra.config.GlobalConstant.COMMA;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/24 17:39 <br>
 * @Author: niujiao
 */
@Slf4j
@Component
public class GoodsAuditApplication {

    @Autowired
    private GoodsAuditService goodsAuditService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GenericService genericService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderApiClient orderApiClient;

    @Autowired
    private GoodsAuditTransfer goodsAuditTransfer;

    @Autowired
    private AuditProcessService auditProcessService;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Autowired
    private FreightTemplateService freightTemplateService;


    @Transactional(rollbackFor = Exception.class)
    public String save(GoodsDto dto) {

        checkSassNo(dto);

        checkSku(dto);
        // 转码
        unescapeContent(dto);

        Lock lock = redisLockRegistry.obtain("APP:GOODS:AUDIT:SAVE:" + dto.getUserId());
        if (lock.tryLock()) {
            try {
                checkGoodsId(dto.getId());
                if (StringUtils.isNotEmpty(dto.getAuditId())) {
                    return doModify(dto);
                } else {
                    return doInsert(dto);
                }
            } finally {
                lock.unlock();
            }
        }
        throw new BaseException(BaseCode.NO_LOCK_ACQUIRED);
    }


    private void checkSku(GoodsDto dto) {
        List<GoodsDetailAttributeDto> attributeList = dto.getAttributeList();

        List<String> skuSpecList = dto.getSkuList().stream().map(SkuDto::getSpecDesc).collect(Collectors.toList());
        List<String> calSkuSpecList = new ArrayList<>();
        if (attributeList.size() == 1) {
            calSkuSpecList.addAll(attributeList.get(0).getAttrValues());
        } else {
            List<List<String>> attrValueList = attributeList.stream().sorted(Comparator.comparing(GoodsDetailAttributeDto::getSn)).map(GoodsDetailAttributeDto::getAttrValues).collect(Collectors.toList());

            calSkuSpecList.addAll(descartes(attrValueList));
        }

        if (skuSpecList.size() != calSkuSpecList.size()) {
            throw new BaseException(FAIL, "商品规格和SKU信息不一致，请重新填写！");
        }

        if (!Sets.newHashSet(skuSpecList).equals(Sets.newHashSet(calSkuSpecList))) {
            throw new BaseException(FAIL, "商品规格和SKU信息不一致，请重新填写！");
        }
    }

    public static List<String> descartes(List<List<String>> lists) {
        List<String> tempList = new ArrayList<>();
        for (List<String> list : lists) {
            if (tempList.isEmpty()) {
                tempList = list;
            } else {
                //java8新特性，stream流
                tempList = tempList.stream().flatMap(item -> list.stream().map(item2 -> item + ASTERISK + item2)).collect(Collectors.toList());
            }
        }
        return tempList;
    }

    private void checkGoodsId(String goodsId) {
        if (StringUtils.isNotEmpty(goodsId)) {
            GoodsEntity goodsEntity = goodsService.getById(goodsId);
            Optional.ofNullable(goodsEntity).orElseThrow(() -> new BaseException(FAIL, "参数错误，商品编码【" + goodsId + "】不存在！"));
            // 同一商品只支持一条审核数据
            LambdaQueryWrapper<GoodsAuditEntity> goodsAuditQuery = Wrappers.<GoodsAuditEntity>lambdaQuery().eq(GoodsAuditEntity::getGoodsId, goodsId).eq(GoodsAuditEntity::getStatus, TO_AUDIT.name());
            GoodsAuditEntity goodsAuditEntity = goodsAuditService.getOne(goodsAuditQuery);
            if (goodsAuditEntity != null) {
                throw new BaseException(FAIL, "商品【" + goodsAuditEntity.getTitle() + "】已提交修改申请，请等待结果！");
            }
        }
    }

    private String doInsert(GoodsDto dto) {
        GoodsAuditEntity goodsAuditEntity = toGoodsAuditEntity(dto, true);
        goodsAuditService.save(goodsAuditEntity);
        // 开启流程
        startAuditProcess(goodsAuditEntity);
        return goodsAuditEntity.getId();
    }

    private void checkSassNo(GoodsDto dto) {
        List<SkuDto> skuList = dto.getSkuList();
        String goodsTypeCode = dto.getGoodsTypeCode();
        if (GoodsTypeEnum.PRE_SELL.name().equals(goodsTypeCode)) {
            skuList.forEach(s -> {
                String sassNo = s.getSaasNo();
                String dealerId = orderApiClient.getDealerIdBySaasNo(dto.getUserId(), sassNo);
                if (StringUtils.isEmpty(dealerId)) {
                    throw new BaseException(FAIL, "打样单号【" + sassNo + "】不存在！");
                } else {
                    s.setDealerId(dealerId);
                }
            });
        } else {
            skuList.forEach(s -> {
                String sassNo = s.getSaasNo();
                if (StringUtils.isEmpty(sassNo)) {
                    return;
                }
                String dealerId = orderApiClient.getDealerIdBySaasNo(dto.getUserId(), sassNo);
                if (StringUtils.isEmpty(dealerId)) {
                    throw new BaseException(FAIL, "打样单号【" + sassNo + "】不存在！");
                } else {
                    s.setDealerId(dealerId);
                }
            });
        }
    }

    private String doModify(GoodsDto dto) {

        String auditId = dto.getAuditId();
        GoodsAuditEntity auditEntity = goodsAuditService.getById(auditId);
        Optional.ofNullable(auditEntity).orElseThrow(() -> new BaseException(FAIL, "参数错误，审核【" + auditId + "】不存在！"));

        dto.setId(auditEntity.getGoodsId());
        GoodsAuditEntity goodsAuditEntity = toGoodsAuditEntity(dto, false);


        // 获取当前审核状态
        if (!(GoodsAuditStatusEnum.TO_AUDIT.name().equals(auditEntity.getStatus()) || REJECT.name().equals(auditEntity.getStatus()))) {
            throw new BaseException(FAIL, "该商品正在审核中或者审核状态已发生变化，请刷新页面查看！");
        }

        LambdaQueryWrapper<GoodsAuditEntity> updateCondition = Wrappers.<GoodsAuditEntity>lambdaQuery().eq(GoodsAuditEntity::getId, auditId).eq(GoodsAuditEntity::getStatus, auditEntity.getStatus());
        boolean result = goodsAuditService.update(goodsAuditEntity, updateCondition);
        if (!result) {
            throw new BaseException(FAIL, "该商品正在审核中或者审核状态已发生变化，请刷新页面查看！");
        }
        // 开启流程
        startAuditProcess(goodsAuditEntity);
        return auditId;
    }

    private void startAuditProcess(GoodsAuditEntity goodsAuditEntity) {

        LambdaQueryWrapper<AuditProcessEntity> auditProcessQuery = Wrappers.<AuditProcessEntity>lambdaQuery().eq(AuditProcessEntity::getAuditId, goodsAuditEntity.getId()).eq(AuditProcessEntity::getHandleNode, OPS.name()).eq(AuditProcessEntity::getAuditStatus, TO_AUDIT.name());

        List<AuditProcessEntity> existAuditProcessList = auditProcessService.list(auditProcessQuery);
        if (!CollectionUtils.isEmpty(existAuditProcessList)) {
            List<String> deletedIds = existAuditProcessList.stream().map(AuditProcessEntity::getId).collect(Collectors.toList());
            auditProcessService.removeByIds(deletedIds);
        }

        AuditProcessEntity auditProcessEntity = new AuditProcessEntity();
        auditProcessEntity.setId(UUIDHelper.getUUID());
        auditProcessEntity.setProcessId(goodsAuditEntity.getId());
        auditProcessEntity.setHandleNode(OPS.name());
        auditProcessEntity.setAuditStatus(TO_AUDIT.name());
        auditProcessEntity.setAuditId(goodsAuditEntity.getId());
        auditProcessEntity.setCreateUser(goodsAuditEntity.getCreateUser());
        auditProcessService.save(auditProcessEntity);
    }

    private boolean moveNextProcess(AuditProcessEntity previousAuditProcessEntity, GoodsAuditEntity goodsAuditEntity) {
        // 经销商商品由ops审核即可
        if (UserTypeEnum.DEALER.name().equals(goodsAuditEntity.getShopType())) {
            return false;
        }
        // 经销商审核直接通过
        if (DEALER.name().equals(previousAuditProcessEntity.getHandleNode())) {
            return false;
        }
        GoodsDto GoodsDto = JSON.parseObject(goodsAuditEntity.getDetailContent(), GoodsDto.class);
        List<String> handlers = GoodsDto.getSkuList().stream().map(SkuDto::getDealerId).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
        // 没有经销商dearId，直接跳过
        if (CollectionUtils.isEmpty(handlers)) {
            return false;
        }


        goodsAuditEntity.setStatus(AUDIT.name());
        goodsAuditService.updateById(goodsAuditEntity);


        List<AuditProcessEntity> list = handlers.stream().map(e -> {
            AuditProcessEntity auditProcessEntity = new AuditProcessEntity();
            auditProcessEntity.setId(UUIDHelper.getUUID());
            auditProcessEntity.setProcessId(previousAuditProcessEntity.getProcessId());
            auditProcessEntity.setStep(previousAuditProcessEntity.getStep() + 1);
            auditProcessEntity.setAuditId(previousAuditProcessEntity.getAuditId());
            auditProcessEntity.setHandleNode(DEALER.name());
            auditProcessEntity.setAuditStatus(TO_AUDIT.name());
            auditProcessEntity.setHandler(e);
            return auditProcessEntity;
        }).collect(Collectors.toList());
        auditProcessService.saveBatch(list);
        return true;
    }

    private GoodsAuditEntity toGoodsAuditEntity(GoodsDto dto, boolean insert) {
        // 获取店铺类型及店铺ID
        String userId = dto.getUserId();
        String shopId = dto.getShopId();
        String shopType = dto.getShopType();

        if (StringUtils.isEmpty(userId)) {
            if (StringUtils.isEmpty(shopId) && StringUtils.isEmpty(shopType)) {
                log.error("userId or shopId,shopType is required");
                throw new BaseException(FAIL);
            }
        }

        if (StringUtils.isNotEmpty(userId)) {
            AppUserVo user = genericService.getUser(userId);
            GenericServiceImpl.GenericShopInfoDto genericShopInfoDto = genericService.getGenericShopInfoByUserId(userId, user.getUserType(), false, true);
            dto.setShopType(user.getUserType());
            dto.setShopId(genericShopInfoDto.getShopId());
        } else {
            GenericServiceImpl.GenericShopInfoDto genericShopInfoDto = genericService.getGenericShopInfoByShopId(shopId, shopType, false, true);
            dto.setUserId(genericShopInfoDto.getUserId());
            dto.setShopType(shopType);
            dto.setShopId(shopId);
        }
        // toModel
        GoodsAuditEntity entity = goodsAuditTransfer.convert(dto);
        if (insert) {
            entity.setCreateUser(dto.getOpUserId());
            entity.setCreateTime(new Date());
        } else {
            entity.setId(dto.getAuditId());
            entity.setUpdateUser(dto.getOpUserId());
            entity.setUpdateTime(new Date());
        }

        List<SkuDto> skuRespDtoList = dto.getSkuList();
        BigDecimal minSellingPrice = skuRespDtoList.stream().min(Comparator.comparing(SkuDto::getPrice)).get().getPrice();
        BigDecimal maxSellingPrice = skuRespDtoList.stream().max(Comparator.comparing(SkuDto::getPrice)).get().getPrice();
        entity.setMinSellingPrice(minSellingPrice);
        entity.setMaxSellingPrice(maxSellingPrice);
        entity.setStatus(TO_AUDIT.name());
        entity.setDetailContent(JSON.toJSONString(dto));
        return entity;
    }


    private void unescapeContent(GoodsDto dto) {
        String content = dto.getContent();
        if (!StringUtils.isEmpty(content)) {
            String unescape = null;
            try {
                unescape = HtmlUtils.unescape(content);
            } catch (ScriptException e) {
                throw new BaseException(FAIL, "转码异常");
            }
            dto.setContent(unescape);
        }
    }

    public GoodsManageAuditDetailVo opsAuditDetail(BaseIdDto data) {
        GoodsManageAuditDetailQueryDto queryDto = new GoodsManageAuditDetailQueryDto().setId(data.getId());
        GoodsManageAuditDetailVo vo = manageAuditDetail(queryDto);
        hideSkuCostPrice(vo);
        return vo;
    }

    /**
     * 隐藏成本价
     *
     * @param vo
     */
    private void hideSkuCostPrice(GoodsManageAuditDetailVo vo) {
        List<SkuDto> skuList = vo.getContent().getSkuList();
        skuList.forEach(e -> e.setCostPrice(BigDecimal.ZERO));
    }


    /**
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    private Boolean audit(GoodsAuditDto dto) {
        Lock lock = redisLockRegistry.obtain("APP:GOODS:AUDIT:EXECUTE:" + dto.getId());
        try {
            if (lock.tryLock(60, TimeUnit.SECONDS)) {
                String auditId = dto.getId();
                String handleNode = dto.getHandleNode();

                GoodsAuditEntity goodsAuditEntity = goodsAuditService.getById(auditId);
                Optional.ofNullable(goodsAuditEntity).orElseThrow(() -> new BaseException(FAIL, "商品审核ID不存在！"));

                // 获取执行步骤
                LambdaQueryWrapper<AuditProcessEntity> auditProcessQuery = Wrappers.<AuditProcessEntity>lambdaQuery().eq(AuditProcessEntity::getAuditId, dto.getId()).eq(AuditProcessEntity::getHandleNode, handleNode);

                if (DEALER.name().equalsIgnoreCase(handleNode)) {
                    auditProcessQuery.eq(StringUtils.isNotEmpty(dto.getAuditUser()), AuditProcessEntity::getHandler, dto.getAuditUser());
                }

                AuditProcessEntity auditProcessEntity = auditProcessService.getOne(auditProcessQuery);
                Optional.ofNullable(auditProcessEntity).orElseThrow(() -> new BaseException(FAIL, "获取审核流程失败，请检查参数！"));
                if (!TO_AUDIT.name().equals(auditProcessEntity.getAuditStatus())) {
                    throw new BaseException(FAIL, "审核状态已发生变化，请刷新页面再操作！");
                }
                // 更新执行步骤状态
                auditProcessEntity.setAuditStatus(dto.getAuditStatus());
                auditProcessEntity.setHandler(dto.getAuditUser());
                auditProcessService.updateById(auditProcessEntity);

                if (REJECT.name().equals(dto.getAuditStatus())) {
                    SpringApplicationContentUtils.getContext().publishEvent(new GoodsAuditRejectedEvent(this, goodsAuditEntity));
                    return true;
                }

                // 执行下一步骤
                if (moveNextProcess(auditProcessEntity, goodsAuditEntity)) {
                    return true;
                }

                // 判断是否全部执行
                auditProcessQuery = Wrappers.<AuditProcessEntity>lambdaQuery().eq(AuditProcessEntity::getAuditId, auditId).eq(AuditProcessEntity::getAuditStatus, TO_AUDIT.name());
                int count = auditProcessService.count(auditProcessQuery);
                if (count == 0) {
                    goodsAuditEntity.setStatus(PASS.name());
                    goodsAuditService.updateById(goodsAuditEntity);
                    SpringApplicationContentUtils.getContext().publishEvent(new GoodsAuditPassedEvent(this, goodsAuditEntity));
                }
                return true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        throw new BaseException(BaseCode.NO_LOCK_ACQUIRED);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean opsAudit(GoodsAuditDto dto) {
        return audit(dto);
    }

    /**
     * 商品管理：审核列表
     *
     * @param searchModel
     * @return
     */
    public PageData<GoodsManageAuditListVo> opsAuditPage(SearchModel<OpsGoodsAuditListQueryDto> searchModel) {

        Page<GoodsAuditEntity> pageParam = new Page<>(searchModel.getPage().getPageNum(), searchModel.getPage().getPageSize());
        Map<String, Object> queryParam = BeanUtil.beanToMap(searchModel.getModel());
        queryParam.put("handleNode", OPS.name());

        Page<Map<String, Object>> listPage = goodsAuditService.auditPage(pageParam, queryParam);
        List<GoodsManageAuditListVo> goodsManageAuditListVos = toGoodsManageAuditListVo(listPage.getRecords());
        if (!CollectionUtils.isEmpty(goodsManageAuditListVos)) {
            goodsManageAuditListVos.forEach(e -> {
                e.setDealerId(null);
                populateGoodsManageAuditListVo(e, null);
            });
        }
        return PageDataUtils.<GoodsManageAuditListVo>build(listPage.getCurrent(), listPage.getSize(), listPage.getTotal(), listPage.getPages(), goodsManageAuditListVos);
    }

    private List<GoodsManageAuditListVo> toGoodsManageAuditListVo(List<Map<String, Object>> records) {
        if (CollectionUtils.isEmpty(records)) {
            return null;
        }
        return records.stream().map(e -> BeanUtil.mapToBean(e, GoodsManageAuditListVo.class, false)).collect(Collectors.toList());
    }

    private void populateGoodsManageAuditListVo(GoodsManageAuditListVo vo, Predicate<? super SkuDto> predicate) {
        String goodsId = vo.getGoodsId();
        if (!StringUtils.isEmpty(goodsId)) {
            LambdaQueryWrapper<GoodsEntity> goodsQuery = Wrappers.<GoodsEntity>lambdaQuery().select(GoodsEntity::getId, GoodsEntity::getStatus).eq(GoodsEntity::getId, goodsId);
            GoodsEntity entity = goodsService.getOne(goodsQuery);
            vo.setStatus(entity.getStatus());
            vo.setStatusDesc(Enum.valueOf(GoodsStatusEnum.class, entity.getStatus()).getDesc());
        }
        vo.setCreateTime(vo.getCreateTime());
        if (vo.getMinSellingPrice() == null || predicate != null) {
            GoodsDto goodsDto = JSON.parseObject(vo.getDetailContent(), GoodsDto.class);
            vo.setSmallImage(goodsDto.getSmallImage());
            vo.setGoodsTypeCode(goodsDto.getGoodsTypeCode());
            List<SkuDto> skuList = goodsDto.getSkuList();
            if (predicate != null) {
                skuList = skuList.stream().filter(predicate).collect(Collectors.toList());
            }
            vo.setMinSellingPrice(skuList.stream().min(Comparator.comparing(SkuDto::getPrice)).get().getPrice());
            vo.setMaxSellingPrice(skuList.stream().max(Comparator.comparing(SkuDto::getPrice)).get().getPrice());
        }
        CategoryEntity categoryEntity = categoryService.getById(vo.getCategoryId());
        vo.setCategoryName(Optional.ofNullable(categoryEntity).map(CategoryEntity::getNamePath).orElse("类目已删除"));
        vo.setShopName(genericService.getGenericShopInfoByShopId(vo.getShopId(), vo.getShopType(), true, false).getShopName());
        // fixme 一期暂时统一返回0
        vo.setTotalStock(0);
        vo.setSaleNumber(0);
        vo.setPurchaseNumber(0);
    }


    /**
     * 商品管理：审核列表
     *
     * @param data
     * @return
     */
    public PageData<GoodsManageAuditListVo> manageAuditPage(SearchModel<GoodsManageAuditPageQueryDto> data) {
        PageData<GoodsManageAuditListVo> page = goodsAuditService.page(data);
        List<GoodsManageAuditListVo> list = page.getList();
        list.forEach(e -> {
            String goodsId = e.getGoodsId();
            if (!StringUtils.isEmpty(goodsId)) {
                LambdaQueryWrapper<GoodsEntity> goodsQuery = Wrappers.<GoodsEntity>lambdaQuery().select(GoodsEntity::getId, GoodsEntity::getStatus).eq(GoodsEntity::getId, goodsId);
                GoodsEntity entity = goodsService.getOne(goodsQuery);
                e.setStatus(entity.getStatus());
                e.setStatusDesc(Enum.valueOf(GoodsStatusEnum.class, entity.getStatus()).getDesc());
            }
        });
        return page;
    }

    public Boolean manageAuditDelete(GoodsManageAuditDeleteDto data) {
        return goodsAuditService.removeByIds(data.getAuditIds(), data.getOpUserId());
    }


    public GoodsManageAuditDetailVo manageAuditDetail(GoodsManageAuditDetailQueryDto queryDto) {
        String userId = queryDto.getUserId();
        LambdaQueryWrapper<GoodsAuditEntity> lambdaQueryWrapper = Wrappers.<GoodsAuditEntity>lambdaQuery().eq(StringUtils.isNotEmpty(userId), GoodsAuditEntity::getUserId, userId).eq(GoodsAuditEntity::getId, queryDto.getId());

        GoodsAuditEntity goodsAuditEntity = goodsAuditService.getOne(lambdaQueryWrapper);

        Optional.ofNullable(goodsAuditEntity).orElseThrow(() -> new BaseException(FAIL, "数据不存在，请核对参数！"));

        GoodsVo goodsVo = JSON.parseObject(goodsAuditEntity.getDetailContent(), GoodsVo.class);
        GoodsManageAuditDetailVo vo = new GoodsManageAuditDetailVo().setId(goodsAuditEntity.getId()).setStatus(goodsAuditEntity.getStatus().equalsIgnoreCase("AUDIT") ? TO_AUDIT.name() : goodsAuditEntity.getStatus()).setContent(goodsVo).setCreateTime(goodsAuditEntity.getCreateTime());
        populateGoodsManageAuditDetailVo(vo);
        return vo;
    }

    private void populateGoodsManageAuditDetailVo(GoodsManageAuditDetailVo vo) {
        // 计算商品最小值
        GoodsVo goodsVo = vo.getContent();
        List<SkuDto> skuList = goodsVo.getSkuList();
        goodsVo.setMinSellingPrice(skuList.stream().min(Comparator.comparing(SkuDto::getPrice)).get().getPrice());
        goodsVo.setMaxSellingPrice(skuList.stream().max(Comparator.comparing(SkuDto::getPrice)).get().getPrice());


        String expressType = goodsVo.getExpressType();
        if (TEMPLATE.name().equals(expressType)) {
            FreightTemplateEntity entity = freightTemplateService.getById(goodsVo.getExpressTemplateId());
            goodsVo.setExpressTemplateId(entity.getId());
            goodsVo.setExpressTemplateName(entity.getName());
            goodsVo.setExpressTemplateType(entity.getType());
        }

        // 属性排序
        List<GoodsDetailAttributeDto> attributeList = goodsVo.getAttributeList();
        if (!CollectionUtils.isEmpty(attributeList)) {
            goodsVo.setAttributeList(attributeList.stream().peek(e -> {
                if (null == e.getSn()) {
                    e.setSn(0);
                }
            }).sorted(Comparator.comparing(GoodsDetailAttributeDto::getSn)).collect(Collectors.toList()));
        }

        // 店铺信息
        GenericServiceImpl.GenericShopInfoDto shopInfoDto = genericService.getGenericShopInfoByShopId(goodsVo.getShopId(), goodsVo.getShopType(), false, false);
        GoodsDetailRespDto.ShopRespDto goodsShopVo = genericService.assembleGoodsShopVo(shopInfoDto);
        // fixme
        GoodsShopVo shopVo = new GoodsShopVo();
        BeanUtils.copyProperties(goodsShopVo, shopVo);
        goodsVo.setShop(shopVo);

        // 分类信息
        CategoryEntity categoryEntity = categoryService.getById(goodsVo.getCategoryId());
        goodsVo.setCategoryName(categoryEntity.getName());
        goodsVo.setCategoryIdPath(categoryEntity.getIdPath());
        goodsVo.setCategoryNamePath(categoryEntity.getNamePath());

        //

    }

    /**
     * 回填goodsId
     *
     * @param auditId
     * @param goodsId
     */
    public void backFillGoodsId(String auditId, String goodsId) {
        GoodsAuditEntity goodsAuditEntity = new GoodsAuditEntity();
        goodsAuditEntity.setId(auditId);
        goodsAuditEntity.setGoodsId(goodsId);
        boolean result = goodsAuditService.updateById(goodsAuditEntity);
        if (!result) {
            throw new BaseException(FAIL, "回填商品信息失敗");
        }
    }

    public PageData<GoodsManageAuditListVo> dealerAuditPage(SearchModel<OpsDealerGoodsAuditListQueryDto> searchModel) {
        Page<GoodsAuditEntity> pageParam = new Page<>(searchModel.getPage().getPageNum(), searchModel.getPage().getPageSize());
        OpsDealerGoodsAuditListQueryDto model = searchModel.getModel();
        Map<String, Object> queryParam = BeanUtil.beanToMap(model);
        queryParam.put("handler", model.getDealerId());
        queryParam.put("handleNode", DEALER.name());
        Page<Map<String, Object>> listPage = goodsAuditService.auditPage(pageParam, queryParam);
        List<GoodsManageAuditListVo> goodsManageAuditListVos = toGoodsManageAuditListVo(listPage.getRecords());
        if (!CollectionUtils.isEmpty(goodsManageAuditListVos)) {
            String dealerId = model.getDealerId();
            if (StringUtils.isNotEmpty(dealerId)) {
                goodsManageAuditListVos.forEach(e -> populateGoodsManageAuditListVo(e, k -> dealerId.equals(k.getDealerId())));
            } else {
                goodsManageAuditListVos.forEach(e -> populateGoodsManageAuditListVo(e, null));
            }

        }
        return PageDataUtils.<GoodsManageAuditListVo>build(listPage.getCurrent(), listPage.getSize(), listPage.getTotal(), listPage.getPages(), goodsManageAuditListVos);
    }


    public GoodsManageAuditDetailVo dealerAuditDetail(GetDealerAuditDetailDto dto) {
        GoodsManageAuditDetailQueryDto queryDto = new GoodsManageAuditDetailQueryDto().setId(dto.getId());
        GoodsManageAuditDetailVo vo = manageAuditDetail(queryDto);
        // 过滤SKU数据
        List<SkuDto> skuList = vo.getContent().getSkuList();
        skuList = skuList.stream().filter(e -> dto.getDealerId().equals(e.getDealerId())).collect(Collectors.toList());
        vo.getContent().setSkuList(skuList);
        return vo;
    }
}
