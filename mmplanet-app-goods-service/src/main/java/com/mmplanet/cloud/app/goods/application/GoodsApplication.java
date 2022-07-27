package com.mmplanet.cloud.app.goods.application;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmplanet.cloud.app.comment.vo.ProductCommentVo;
import com.mmplanet.cloud.app.common.dto.BaseIdDto;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.Page;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManagePageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsPageQuery;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsAttributeRespDto;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsDetailRespDto;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsListRespDto;
import com.mmplanet.cloud.app.goods.domain.event.GoodsAccessEvent;
import com.mmplanet.cloud.app.goods.domain.service.*;
import com.mmplanet.cloud.app.goods.domain.service.impl.GenericServiceImpl;
import com.mmplanet.cloud.app.goods.domain.transfer.GoodsTransfer;
import com.mmplanet.cloud.app.goods.domain.transfer.SkuTransfer;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.enums.DelayedTaskTriggerActionEnum;
import com.mmplanet.cloud.app.goods.enums.DelayedTaskTypeEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum;
import com.mmplanet.cloud.app.goods.infra.config.GlobalConstant;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.infra.entity.SkuEntity;
import com.mmplanet.cloud.app.goods.infra.integration.FavoriteApiClient;
import com.mmplanet.cloud.app.goods.infra.integration.GoodsCommentApiClient;
import com.mmplanet.cloud.app.goods.infra.integration.UserApiClient;
import com.mmplanet.cloud.app.goods.infra.integration.UserBlacklistApiClient;
import com.mmplanet.cloud.app.goods.infra.util.PageDataUtils;
import com.mmplanet.cloud.app.goods.infra.util.SpringApplicationContentUtils;
import com.mmplanet.cloud.app.goods.vo.*;
import com.mmplanet.cloud.app.user.dto.UserBlacklistQueryDto;
import com.mmplanet.cloud.app.user.enums.BlacklistEnum;
import com.mmplanet.cloud.app.user.enums.FavoriteTypeEnum;
import com.mmplanet.cloud.app.user.vo.IsFavoriteVo;
import com.mmplanet.cloud.app.user.vo.UserBlacklistVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.common.code.BaseCode.FAIL;
import static com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum.FREE;
import static com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum.UNIFIED;
import static com.mmplanet.cloud.app.goods.enums.GoodsStockReduceEnum.ORDER_REDUCE;
import static com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum.PRE_SELL;
import static com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum.PRI;
import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;

/**
 * 商品表 应用层
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Component
public class GoodsApplication {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsQueryService goodsQueryService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private GoodsAttributeService goodsAttributeService;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private GoodsTransfer goodsTransfer;

    @Autowired
    private FavoriteApiClient favoriteApiClient;

    @Autowired
    private GenericService genericService;

    @Autowired
    private DelayedTaskService delayedTaskService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserBlacklistApiClient userBlacklistApiClient;

    @Autowired
    private GoodsCommentApiClient goodsCommentApiClient;

    @Autowired
    private SkuTransfer skuTransfer;

    /**
     * 商品搜索
     */
    public PageData<GoodsListVo> pageQuery(Page pageParam, GoodsPageQuery query) {
        setUserBlackList(query);
        PageData<GoodsListRespDto> ret = goodsQueryService.pageQuery(pageParam.getPageNum(), pageParam.getPageSize(), query);
        Page page = ret.getPage();
        return PageDataUtils.build(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getHasNextPage(),
                goodsTransfer.assembleGoodsListVo(ret.getList()));
    }

    /**
     * 店铺首页
     *
     * @param query
     * @return
     */
    public PageData<GoodsListVo> shopHomePage(Page pageParam, GoodsPageQuery query) {
        Integer pageNum = pageParam.getPageNum();
        Integer pageSize = pageParam.getPageSize();
        PageData<GoodsListRespDto> ret = goodsQueryService.shopHomePage(pageNum, pageSize, query);
        Page page = ret.getPage();
        return PageDataUtils.build(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getHasNextPage(),
                goodsTransfer.assembleGoodsListVo(ret.getList()));
    }

    /**
     * 商品管理：商品列表查询
     *
     * @param query
     * @return
     */
    public PageData<GoodsManageListVo> managePage(Page pageParam, GoodsManagePageQuery query) {
        Integer pageNum = pageParam.getPageNum();
        Integer pageSize = pageParam.getPageSize();
        PageData<GoodsListRespDto> ret = goodsQueryService.managePageQuery(pageNum, pageSize, query);
        Page page = ret.getPage();
        return PageDataUtils.build(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getHasNextPage(),
                goodsTransfer.assembleGoodsManageListVo(ret.getList()));
    }

    /**
     * 商品管理：ops商品列表查询
     *
     * @param pageParam
     * @return
     */
    public PageData<GoodsManageListVo> opsManagePage(Page pageParam,GoodsManagePageQuery query) {
        query.setSort(new SortDto("CREATE_TIME", "DESC"));
        return managePage(pageParam,query);
    }

    /**
     * 设置用户黑名单
     *
     * @param query
     */
    private void setUserBlackList(GoodsPageQuery query) {
        String userId = query.getUserId();
        // 获取当前用户
        UserBlacklistQueryDto queryDto = new UserBlacklistQueryDto();
        queryDto.setUserId(userId);
        queryDto.setBusinessType(BlacklistEnum.GOODS.name());
        PageData<UserBlacklistVo> page = userBlacklistApiClient.page(1, 200, queryDto);
        List<UserBlacklistVo> list = page.getList();
        if (!CollectionUtils.isEmpty(list)) {
            query.setExcludeIds(list.stream().map(UserBlacklistVo::getBusinessId).collect(Collectors.toList()));
        }
    }


    /**
     * 根据id查询
     */
    public GoodsDetailVo detail(BaseIdDto baseIdDto) {
        // 商品基础信息
        GoodsDetailRespDto goodsDetailRespDto = goodsQueryService.detail(baseIdDto.getId(), null);
        return goodsTransfer.assembleGoodsDetailVo(goodsDetailRespDto);
    }

    public GoodsDetailVo detailV1(GoodsDetailQueryDto dto) {

        String userId = dto.getUserId();
        // 商品基础信息
        GoodsDetailRespDto resp = goodsQueryService.detail(dto.getId(), entity -> {
            if (PRI.name().equals(entity.getGoodsTypeCode())
                    && !StringUtils.isEmpty(entity.getBuyerId())
                    && !Objects.equals(entity.getBuyerId(), "NONE")) {
                boolean access = userId.equals(entity.getBuyerId()) || userId.equals(entity.getUserId());
                if (!access) {
                    throw new BaseException(FAIL, "无权访问该商品");
                }
            }
        });
        // 是否收藏
        populateFavorite(userId, resp);
        // 填充店铺信息
        populateShop(resp);
        // 获取评论
        populateComment(resp);

        GoodsDetailVo result = goodsTransfer.assembleGoodsDetailVo(resp);

        // 发布访问事件
        SpringApplicationContentUtils.getContext().publishEvent(new GoodsAccessEvent(this,
                new GoodsAccessEvent.Item(result.getId(), result.getShopId(), userId)));
        return result;
    }

    public GoodsManageDetailVo opsDetail(BaseIdDto data) {
        GoodsManageDetailDto dto = new GoodsManageDetailDto().setId(data.getId());
        GoodsManageQuery query = new GoodsManageQuery();
        query.setId(dto.getId());
        return manageDetail(query);
    }


    public GoodsManageDetailVo manageDetail(GoodsManageQuery query) {
        // 商品基础信息
        GoodsDetailRespDto respDto = goodsQueryService.manageDetail(query);
        // 店铺信息
        populateShop(respDto);
        return goodsTransfer.assembleGoodsManageDetailVo(respDto);
    }

    /**
     * 修改
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(GoodsDto dto) {
        String goodsId = dto.getId();
        List<SkuDto> skuRespDtoList = dto.getSkuList();
        // 修改商品信息
        GoodsEntity goodsEntity = toGoodsEntityModel(dto, false);
        // 计算属性是否变更
        boolean attrHasChange = calAttrHasChange(goodsId, dto.getAttributeList());
        // 修改SKU信息 计算新增和修改
        List<SkuEntity> skuList = skuService.findByGoodsId(goodsId);
        Map<String, SkuEntity> skuMap = skuList.stream().collect(Collectors.toMap(SkuEntity::getSpecDesc, Function.identity()));
        List<SkuDto> insertAndUpdateList = calSkuChanged(goodsId, skuRespDtoList, skuMap);

        goodsService.updateById(goodsEntity);
        if (attrHasChange) {
            goodsAttributeService.deleteGoodsAttribute(goodsId);
            goodsAttributeService.saveGoodsAttribute(goodsEntity, dto.getAttributeList());
        }
        // 删除无效的sku
        Set<String> deleteSkuIdList = skuMap.values().stream().map(SkuEntity::getId).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(deleteSkuIdList)) {
            skuService.removeByIds(deleteSkuIdList);
        }
        // 新增或变更
        List<SkuEntity> skuEntities = insertAndUpdateList.stream().map(e -> skuTransfer.toModel(e)).collect(Collectors.toList());
        skuService.save(skuEntities);

        // 4. 延迟任务
        addDelayedTask(dto.getId(), dto.getGoodsTypeCode(), dto.getActivityEndTime());
    }

    private boolean calAttrHasChange(String goodsId, List<GoodsDetailAttributeDto> reqData) {
        Map<String, GoodsAttributeRespDto> existedAttrMap = goodsAttributeService.queryGoodsAttribute(goodsId)
                .stream().collect(Collectors.toMap(GoodsAttributeRespDto::getAttrName, Function.identity()));
        for (GoodsDetailAttributeDto e : reqData) {
            // 校验名称是否一致
            String attrName = e.getAttrName();
            if (existedAttrMap.containsKey(attrName)) {
                GoodsAttributeRespDto obj = existedAttrMap.get(attrName);
                if (!(Objects.equals(e.getSn(), obj.getSn())
                        && Objects.equals(Sets.newHashSet(e.getAttrValues()), Sets.newHashSet(obj.getAttrValues())))) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private List<SkuDto> calSkuChanged(String goodsId, List<SkuDto> skuRespDtoList, Map<String, SkuEntity> skuVoMap) {
        List<SkuDto> insertAndUpdateList = new ArrayList<>();
        skuRespDtoList.forEach(e -> {
            // 校验名称是否一致
            String specDesc = e.getSpecDesc();
            if (skuVoMap.containsKey(specDesc)) {
                e.setId(skuVoMap.remove(specDesc).getId());
                // update
                insertAndUpdateList.add(e);
            } else {
                // insert
                e.setGoodsId(goodsId);
                insertAndUpdateList.add(e);
            }
        });
        return insertAndUpdateList;
    }

    /**
     * 新增商品
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public String add(GoodsDto dto) {
        // 商品信息
        GoodsEntity entity = toGoodsEntityModel(dto, true);
        goodsService.save(entity);
        // 保存商品属性
        goodsAttributeService.saveGoodsAttribute(entity, dto.getAttributeList());
        // 保存SKU信息
        List<SkuEntity> skuEntities = toSkuEntityModel(entity, dto.getSkuList());
        skuService.saveBatch(skuEntities);
        // 预售商品添加延迟任务
        addDelayedTask(entity.getId(), entity.getGoodsTypeCode(), entity.getActivityEndTime());
        return entity.getId();
    }

    private List<SkuEntity> toSkuEntityModel(GoodsEntity entity, List<SkuDto> skuList) {
        return skuList.stream().map(e -> {
            SkuEntity skuEntity = skuTransfer.toModel(e);
            skuEntity.setGoodsId(entity.getId());
            skuEntity.setCreateTime(entity.getCreateTime());
            skuEntity.setCreateUser(entity.getCreateUser());
            return skuEntity;
        }).collect(Collectors.toList());
    }

    private GoodsEntity toGoodsEntityModel(GoodsDto dto, boolean insert) {

        GoodsEntity entity = goodsTransfer.toModel(dto);

        List<SkuDto> skuRespDtoList = dto.getSkuList();
        BigDecimal minSellingPrice = skuRespDtoList.stream().min(Comparator.comparing(SkuDto::getPrice)).get().getPrice();
        BigDecimal maxSellingPrice = skuRespDtoList.stream().max(Comparator.comparing(SkuDto::getPrice)).get().getPrice();
        entity.setCategoryPath(categoryService.getById(entity.getCategoryId()).getIdPath());
        String expressType = dto.getExpressType();
        if (UNIFIED.name().equals(expressType)) {
            entity.setExpressTypeValue(JSON.toJSONString(dto.getUnifiedExpress()));
        } else if (FREE.name().equals(expressType)) {
            entity.setExpressTypeValue("0");
        } else {
            entity.setExpressTypeValue(dto.getExpressTemplateId());
        }
        entity.setBuyerId(PRI.name().equals(entity.getGoodsTypeCode()) ? "NONE" : null);
        entity.setMinSellingPrice(minSellingPrice);
        entity.setMaxSellingPrice(maxSellingPrice);
        entity.setTotalStock(skuRespDtoList.stream().mapToInt(SkuDto::getStock).sum());
        entity.setImages(String.join(GlobalConstant.COMMA, dto.getImages()));

        if (insert) {
            entity.setCreateTime(new Date());
            entity.setCreateUser(dto.getOpUserId());
            entity.setStatus(GoodsStatusEnum.INIT.name());
        } else {
            entity.setUpdateTime(new Date());
            entity.setUpdateUser(dto.getOpUserId());
        }
        return entity;
    }

    /**
     * 预收商品，添加延迟任务
     */
    private void addDelayedTask(String goodsId, String goodsTypeCode, Date triggerTime) {

        if (!PRE_SELL.name().equals(goodsTypeCode)) {
            return;
        }
        delayedTaskService.save(goodsId, DelayedTaskTypeEnum.GOODS.name(), DelayedTaskTriggerActionEnum.GOODS_DOWN.name(), triggerTime, null);
    }

    public Boolean manageUpAndDown(GoodsUpAndDownDto requestBean) {
        return goodsService.manageUpAndDown(requestBean);
    }

    public GoodsManageStatisticsVo manageStatistics(GoodsManageStatisticsQueryDto requestBean) {
        // fixme 临时通过SQL语句，后期改成单独统一表
        return goodsService.manageStatistics(requestBean);
    }

    public Boolean manageDelete(GoodsManageDeleteDto requestBean) {
        return goodsService.removeByIds(requestBean.getGoodsIds(), requestBean.getOpUserId());
    }

    public Boolean manageTop(GoodsManageTopDto requestBean) {
        return goodsService.manageTop(requestBean);
    }


    @Transactional(rollbackFor = Exception.class)
    public Boolean opsDelete(GoodsManageDeleteDto deleteDto) {
        return manageDelete(deleteDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean opsModify(GoodsDto dto) {
        // 获取店铺所有者userId
        String shopId = dto.getShopId();
        String shopType = dto.getShopType();
        GenericServiceImpl.GenericShopInfoDto shopInfoDto = genericService.getGenericShopInfoByUserId(shopId, shopType, true, true);
        dto.setUserId(shopInfoDto.getUserId());

        modify(dto);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean opsAdd(GoodsDto dto) {
        // 获取店铺所有者userId
        String shopId = dto.getShopId();
        String shopType = dto.getShopType();
        GenericServiceImpl.GenericShopInfoDto shopInfoDto = genericService.getGenericShopInfoByUserId(shopId, shopType, true, true);
        dto.setUserId(shopInfoDto.getUserId());

        add(dto);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean opsUpAndDown(GoodsUpAndDownDto dto) {
        return manageUpAndDown(dto);
    }


    public String createPostageGoods(CreatePostageGoodsDto dto) {

        LambdaQueryWrapper<GoodsEntity> queryWrapper = Wrappers.<GoodsEntity>lambdaQuery().eq(GoodsEntity::getShopId, dto.getShopId()).eq(GoodsEntity::getGoodsTypeCode, GoodsTypeEnum.POSTAGE.name()).eq(GoodsEntity::getMinSellingPrice, dto.getPrice());

        GoodsEntity goodsEntity = goodsService.getOne(queryWrapper);
        if (goodsEntity == null) {
            SkuDto skuRespDto = new SkuDto();
            skuRespDto.setSpecDesc("补邮");
            skuRespDto.setPrice(dto.getPrice());
            skuRespDto.setOriginalPrice(dto.getPrice());
            skuRespDto.setStock(Integer.MAX_VALUE);

            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setUserId(dto.getUserId());
            goodsDto.setShopId(dto.getShopId());
            goodsDto.setCategoryId("");
            goodsDto.setTitle("补邮商品");
            goodsDto.setGoodsTypeCode(GoodsTypeEnum.POSTAGE.name());
            goodsDto.setSmallImage("");
            goodsDto.setImages(Lists.newArrayList());
            goodsDto.setStockReduce(ORDER_REDUCE.name());
            goodsDto.setExpressType(FREE.name());
            goodsDto.setExpressTypeValue("0");
            goodsDto.setSkuList(Lists.newArrayList(skuRespDto));
            goodsDto.setAttributeList(Lists.newArrayList());
            return add(goodsDto);
        } else {
            return goodsEntity.getId();
        }
    }

    public List<GoodsListVo> get(BaseIdsDto dto) {
        List<String> goodsIds = dto.getIds();
        Collection<GoodsEntity> goodsEntities = goodsService.listByIds(goodsIds);
        if (CollectionUtils.isEmpty(goodsEntities)) {
            return new ArrayList<GoodsListVo>();
        }
        return goodsEntities.stream().map(e -> {
            GoodsListVo vo = new GoodsListVo();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    private void populateShop(GoodsDetailRespDto result) {
        GenericServiceImpl.GenericShopInfoDto shopInfoDto = genericService.getGenericShopInfoByShopId(result.getShopId(), result.getShopType(), true, false);
        GoodsDetailRespDto.ShopRespDto shopRespDto = genericService.assembleGoodsShopVo(shopInfoDto);
        result.setShopRespDto(shopRespDto);
    }


    private void populateFavorite(String userId, GoodsDetailRespDto result) {
        IsFavoriteVo favorite = favoriteApiClient.isFavorite(userId, FavoriteTypeEnum.GOODS.name(), result.getId());
        result.setFavorite(favorite.isFavorite());
        result.setFavoriteId(favorite.getId());
    }

    private void populateComment(GoodsDetailRespDto result) {
        PageData<ProductCommentVo> page = goodsCommentApiClient.page(1, 3, result.getId());
        Long total = page.getPage().getTotal();
        if (total > 0) {
            List<ProductCommentVo> list = page.getList();
            List<GoodsDetailRespDto.CommentItemRespDto> commentItems = list.stream().map(e -> {
                GoodsDetailRespDto.CommentItemRespDto commentItemRespDto = new GoodsDetailRespDto.CommentItemRespDto();
                commentItemRespDto.setAnonymous(e.getAnonymous());
                commentItemRespDto.setCommentImageArray(e.getCommentImageArray());
                commentItemRespDto.setUserNickname(e.getUserNickname());
                commentItemRespDto.setUserHeaderImage(e.getUserHeaderImage());
                commentItemRespDto.setContent(e.getContent());
                commentItemRespDto.setCreateTime(e.getCreateTime());
                return commentItemRespDto;
            }).collect(Collectors.toList());

            GoodsDetailRespDto.CommentRespDto commentRespDto = new GoodsDetailRespDto.CommentRespDto();
            commentRespDto.setTotal(total);
            commentRespDto.setItems(commentItems);
            result.setCommentRespDto(commentRespDto);
        }
    }

    public SimpleGoodsVo simpleDetail(BaseIdDto dto) {
        String goodsId = dto.getId();
        GoodsEntity goodsEntity = goodsService.getById(goodsId);
        Optional.ofNullable(goodsEntity).orElseThrow(() -> new BaseException(FAIL, "商品信息不存在"));

        SimpleGoodsVo result = goodsTransfer.assembleSimpleGoodsVo(goodsEntity);

        CategoryEntity categoryEntity = categoryService.getById(result.getCategoryId());
        result.setCategoryName(categoryEntity.getName());
        result.setCategoryIdPath(categoryEntity.getIdPath());
        result.setCategoryNamePath(categoryEntity.getNamePath());
        return result;
    }

    public Boolean bindUser(GoodsBindUserDto dto) {
        GoodsEntity goodsEntity = goodsService.getById(dto.getGoodsId());
        Optional.ofNullable(goodsEntity).orElseThrow(() -> new BaseException(FAIL, "商品信息不存在"));
        if (!PRI.name().equals(goodsEntity.getGoodsTypeCode())) {
            throw new BaseException(FAIL, "非私发商品不允许绑定用户");
        }
        if (goodsEntity.getBuyerId().equals("NONE")) {
            goodsEntity.setBuyerId(dto.getUserId());
            goodsService.updateById(goodsEntity);
        }
        return true;
    }

    public Boolean endActivity(EndActivityDto dto) {
        GoodsEntity goodsEntity = goodsService.getById(dto.getGoodsId());
        Optional.ofNullable(goodsEntity).orElseThrow(() -> new BaseException(FAIL, "商品信息不存在"));
        if (!PRE_SELL.name().equals(goodsEntity.getGoodsTypeCode())) {
            throw new BaseException(FAIL, "非预售商品没有结束活动操作！");
        }
        goodsEntity.setActivityEnd(TRUE.getValue());
        goodsEntity.setStatus(GoodsStatusEnum.DOWN.name());
        return goodsService.updateById(goodsEntity);
    }
}
