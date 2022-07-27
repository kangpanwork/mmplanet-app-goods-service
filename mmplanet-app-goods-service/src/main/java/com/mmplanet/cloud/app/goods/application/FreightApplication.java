package com.mmplanet.cloud.app.goods.application;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.goods.domain.service.FreightTemplateRuleService;
import com.mmplanet.cloud.app.goods.domain.service.FreightTemplateService;
import com.mmplanet.cloud.app.goods.domain.service.GenericService;
import com.mmplanet.cloud.app.goods.domain.service.GoodsService;
import com.mmplanet.cloud.app.goods.domain.service.impl.GenericServiceImpl;
import com.mmplanet.cloud.app.goods.domain.transfer.FreightTemplateTransfer;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateEntity;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateRuleEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.infra.util.PageDataUtils;
import com.mmplanet.cloud.app.goods.vo.*;
import com.mmplanet.cloud.app.user.vo.AppUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.common.code.BaseCode.FAIL;
import static com.mmplanet.cloud.app.common.code.BaseCode.NO_LOCK_ACQUIRED;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/8 14:44 <br>
 * @Author: niujiao
 */
@Component
public class FreightApplication {

    @Autowired
    private FreightTemplateService freightTemplateService;

    @Autowired
    private FreightTemplateRuleService freightTemplateRuleService;

    @Autowired
    private FreightTemplateTransfer freightTemplateTransfer;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GenericService genericService;

    @Autowired
    private RedisLockRegistry redisLockRegistry;


    /**
     * 删除模板ID
     *
     * @param templateIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean del(List<String> templateIds) {
        return freightTemplateService.removeByIds(templateIds);
    }

    /**
     * 删除模板规则ID
     *
     * @param templateRuleIds
     * @return
     */
    public boolean delTemplateRule(List<String> templateRuleIds) {
        return freightTemplateRuleService.removeByIds(templateRuleIds);
    }

    /**
     * 新增/修改模板
     * @param dto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String save(FreightTemplateDto dto) {

        String shopId = dto.getShopId();
        if (StringUtils.isEmpty(shopId)) {
            AppUserVo user = genericService.getUser(dto.getUserId());
            GenericServiceImpl.GenericShopInfoDto genericShopInfoDto = genericService.getGenericShopInfoByUserId(user.getId(), user.getUserType(), true, false);
            dto.setShopId(genericShopInfoDto.getShopId());
        }

        checkNoDuplicateArea(dto);

        Lock lock = redisLockRegistry.obtain(String.join(":", "APP:GOODS:FREIGHT:CREATE", dto.getUserId()));
        if (lock.tryLock()) {
            try {
                String templateId = dto.getId();
                List<FreightTemplateRuleDto> ruleDtoList = dto.getRules();

                FreightTemplateEntity freightTemplateEntity = freightTemplateTransfer.convertFreightTemplate(dto);
                freightTemplateEntity.setChargeType("CUSTOM");
                freightTemplateEntity.setExtendField(JSON.toJSONString(dto.getDefaultRule()));
                freightTemplateEntity.setCreateUser(dto.getUserId());
                freightTemplateEntity.setUpdateUser(dto.getUserId());

                List<FreightTemplateRuleEntity> ruleEntities = ruleDtoList.stream().map(rule -> {
                    FreightTemplateRuleEntity ruleEntity = freightTemplateTransfer.convertFreightTemplateRule(rule);
                    ruleEntity.setChargeWay(dto.getChargeWay());
                    ruleEntity.setRenewCharge(dto.getDefaultRule().getRenewCharge());
                    ruleEntity.setCreateUser(dto.getUserId());
                    ruleEntity.setUpdateUser(dto.getUserId());
                    return ruleEntity;
                }).collect(Collectors.toList());

                if(StringUtils.isEmpty(templateId)){
                    freightTemplateService.save(freightTemplateEntity,ruleEntities);
                    templateId = freightTemplateEntity.getId();
                }else{
                    freightTemplateService.modify(freightTemplateEntity,ruleEntities);
                }
                return templateId;
            } finally {
                lock.unlock();
            }
        }
        throw new BaseException(NO_LOCK_ACQUIRED);
    }

    public FreightTemplateVo detail(String templateId) {
        FreightTemplateEntity freightTemplateEntity = freightTemplateService.getById(templateId);
        Optional.ofNullable(freightTemplateEntity).orElseThrow(() -> new BaseException(FAIL, "模板不存在"));

        LambdaQueryWrapper<FreightTemplateRuleEntity> ruleQuery = Wrappers.<FreightTemplateRuleEntity>lambdaQuery()
                .eq(FreightTemplateRuleEntity::getTemplateId, templateId);
        List<FreightTemplateRuleEntity> ruleEntities = freightTemplateRuleService.list(ruleQuery);

        FreightTemplateVo vo = freightTemplateTransfer.assembleFreightTemplateVo(freightTemplateEntity);
        List<FreightTemplateRuleVo> ruleVos = freightTemplateTransfer.assembleFreightTemplateVo(ruleEntities);
        vo.setRules(ruleVos);
        vo.setDefaultRule(JSON.parseObject(freightTemplateEntity.getExtendField(), FreightTemplateDefaultRuleVo.class));
        return vo;
    }


    /**
     * 校验地区是否重复
     *
     * @param dto
     */
    private void checkNoDuplicateArea(FreightTemplateDto dto) {
        List<FreightTemplateRuleDto> rules = dto.getRules();
        List<FreightTemplateRuleDto> filterRules = rules.stream().filter(distinctByKey(e -> String.join(":", e.getProvinceCode(), e.getCityCode(), e.getAreaCode())))
                .collect(Collectors.toList());
        if (rules.size() != filterRules.size()) {
            throw new BaseException(FAIL, "地区存在重复数据！");
        }
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public PageData<FreightTemplateListVo> page(SearchModel<FreightTemplateQueryDto> searchQuery) {

        FreightTemplateQueryDto model = searchQuery.getModel();
        // 获取店铺ID或工厂ID
        AppUserVo user = genericService.getUser(model.getUserId());
        GenericServiceImpl.GenericShopInfoDto genericShopInfoDto = genericService.getGenericShopInfoByUserId(model.getUserId(), user.getUserType(), false, false);
        model.setShopId(genericShopInfoDto.getShopId());

        // 执行业务逻辑
        LambdaQueryWrapper<FreightTemplateEntity> lambdaQueryWrapper = Wrappers.<FreightTemplateEntity>lambdaQuery()
                .eq(FreightTemplateEntity::getShopId, model.getShopId())
                .eq(!StringUtils.isEmpty(model.getType()), FreightTemplateEntity::getType, model.getType());

        Page<FreightTemplateEntity> page = new Page<>(searchQuery.getPage().getPageNum(), searchQuery.getPage().getPageSize());
        IPage<FreightTemplateEntity> resultPage = freightTemplateService.page(page, lambdaQueryWrapper);

        List<FreightTemplateListVo> data = freightTemplateTransfer.assembleFreightTemplateListVo(resultPage.getRecords());
        return PageDataUtils.<FreightTemplateListVo>build(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal(), resultPage.getPages(), data);
    }

    public Integer calFreight(CalFreightDto dto) {
        String goodsId = dto.getGoodsId();
        GoodsEntity goodsEntity = goodsService.getById(goodsId);
        return doCalFreight(dto.getProvinceCode(), dto.getCityCode(), dto.getAreaCode(), dto.getNum(), goodsEntity);
    }

    private int doCalFreight(String provinceCode, String cityCode, String areaCode, Integer num, GoodsEntity goodsEntity) {
        String expressType = goodsEntity.getExpressType();
        if (GoodsExpressTypeEnum.FREE.name().equals(expressType)) {
            return 0;
        }
        if (GoodsExpressTypeEnum.UNIFIED.name().equals(expressType)) {
            String expressTypeValue = goodsEntity.getExpressTypeValue();
            UnifiedExpressDto unifiedExpressDto = JSON.parseObject(expressTypeValue, UnifiedExpressDto.class);
            String renewCharge = unifiedExpressDto.getRenewCharge();
            if (StringUtils.isEmpty(renewCharge)) {
                renewCharge = "0";
            }
            return new BigDecimal(unifiedExpressDto.getFirstCharge()).multiply(ONE_HUNDRED)
                    .add(new BigDecimal(renewCharge).multiply(ONE_HUNDRED).multiply(new BigDecimal(num - 1))).intValue();
        }

        String templateId = goodsEntity.getExpressTypeValue();

        LambdaQueryWrapper<FreightTemplateRuleEntity> freightTemplateRuleEntityLambdaQueryWrapper = Wrappers.<FreightTemplateRuleEntity>lambdaQuery()
                .eq(FreightTemplateRuleEntity::getTemplateId, templateId)
                .eq(FreightTemplateRuleEntity::getProvinceCode, provinceCode);
        List<FreightTemplateRuleEntity> ruleEntities = freightTemplateRuleService.list(freightTemplateRuleEntityLambdaQueryWrapper);
        // 匹配省、市、区
        FreightTemplateRuleEntity rule = lookUpTemplateRule(provinceCode, cityCode, areaCode, ruleEntities);
        if (rule == null) {
            // 获取默认模板
            FreightTemplateEntity template = freightTemplateService.getById(templateId);
            rule = JSON.parseObject(template.getExtendField(), FreightTemplateRuleEntity.class);
        }
        BigDecimal firstCharge = rule.getFirstCharge();
        BigDecimal firstChargeRange = rule.getFirstChargeRange();

        BigDecimal diff = new BigDecimal(num).subtract(firstChargeRange);
        if (diff.compareTo(BigDecimal.ZERO) >= 1) {
            firstCharge = firstCharge.add(diff.subtract(rule.getRenewCharge()));
        }
        return firstCharge.multiply(ONE_HUNDRED).intValue();
    }


    private FreightTemplateRuleEntity lookUpTemplateRule(String provinceCode, String cityCode, String areaCode, List<FreightTemplateRuleEntity> ruleEntities) {

        // 匹配省市区
        Optional<FreightTemplateRuleEntity> matchOptional = ruleEntities.stream()
                .filter(e ->
                        provinceCode.equals(e.getProvinceCode())
                                && cityCode.equals(e.getCityCode())
                                && areaCode.equals(e.getAreaCode())).findFirst();
        if (matchOptional.isPresent()) {
            return matchOptional.get();
        }

        // 匹配省市
        matchOptional = ruleEntities.stream()
                .filter(e ->
                        provinceCode.equals(e.getProvinceCode()) && cityCode.equals(e.getCityCode())).findFirst();
        if (matchOptional.isPresent()) {
            return matchOptional.get();
        }

        // 匹配省
        matchOptional = ruleEntities.stream()
                .filter(e -> provinceCode.equals(e.getProvinceCode())).findFirst();
        return matchOptional.orElse(null);
    }

    private final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    public CalFreightV2Vo calFreightV2(CalFreightV2Dto dto) {
        List<CalFreightV2Dto.Item> items = dto.getItems();

        List<String> goodsIdSet = items.stream().map(CalFreightV2Dto.Item::getGoodsId).collect(Collectors.toList());
        List<GoodsEntity> goodsEntities = goodsService.listByIds(goodsIdSet);
        if (goodsEntities.size() != goodsIdSet.size()) {
            throw new BaseException(FAIL, "运费计算异常，请检查参数！");
        }
        Map<String, Integer> itemMap = items.stream().collect(Collectors.toMap(CalFreightV2Dto.Item::getGoodsId, CalFreightV2Dto.Item::getNum));
        Map<String, List<GoodsEntity>> goodsGroup = goodsEntities.stream().collect(Collectors.groupingBy(GoodsEntity::getShopId));

        List<CalFreightV2Vo.Item> freightItems = new ArrayList<>();
        goodsGroup.forEach((e, v) -> {
            Integer freightCost = v.stream().map(k -> doCalFreight(dto.getProvinceCode(), dto.getCityCode(),
                    dto.getAreaCode(), itemMap.get(k.getId()), k)).reduce(0, Integer::sum);
            CalFreightV2Vo.Item item = new CalFreightV2Vo.Item();
            item.setShopId(e);
            item.setFreightCost(freightCost);
            freightItems.add(item);
        });
        return new CalFreightV2Vo().setItems(freightItems);
    }
}
