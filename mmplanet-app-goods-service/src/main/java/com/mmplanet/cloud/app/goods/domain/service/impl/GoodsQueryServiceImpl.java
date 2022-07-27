package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManagePageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsPageQuery;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsDetailRespDto;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsListRespDto;
import com.mmplanet.cloud.app.goods.domain.service.GenericService;
import com.mmplanet.cloud.app.goods.domain.service.GoodsAttributeService;
import com.mmplanet.cloud.app.goods.domain.service.GoodsQueryService;
import com.mmplanet.cloud.app.goods.domain.transfer.GoodsTransfer;
import com.mmplanet.cloud.app.goods.domain.transfer.SkuTransfer;
import com.mmplanet.cloud.app.goods.dto.SortDto;
import com.mmplanet.cloud.app.goods.enums.*;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryEntity;
import com.mmplanet.cloud.app.goods.infra.entity.FreightTemplateEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.infra.entity.SkuEntity;
import com.mmplanet.cloud.app.goods.infra.integration.ShoppingCardApiClient;
import com.mmplanet.cloud.app.goods.infra.mapper.CategoryMapper;
import com.mmplanet.cloud.app.goods.infra.mapper.FreightTemplateMapper;
import com.mmplanet.cloud.app.goods.infra.mapper.GoodsMapper;
import com.mmplanet.cloud.app.goods.infra.mapper.SkuMapper;
import com.mmplanet.cloud.app.goods.infra.util.PageDataUtils;
import com.mmplanet.cloud.app.goods.vo.UnifiedExpressVo;
import com.mmplanet.cloud.app.shoppingcard.vo.ShoppingCardPurchaseNumVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum.FREE;
import static com.mmplanet.cloud.app.goods.enums.GoodsExpressTypeEnum.UNIFIED;
import static com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum.PRE_SELL;
import static com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum.PRI;
import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/23 11:54 <br>
 * @Author: niujiao
 */
@Service
public class GoodsQueryServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsQueryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodsTransfer goodsTransfer;

    @Autowired
    private GenericService genericService;

    @Autowired
    private ShoppingCardApiClient shoppingCardApiClient;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private GoodsAttributeService goodsAttributeService;

    @Autowired
    private FreightTemplateMapper freightTemplateMapper;

    @Autowired
    private SkuTransfer skuTransfer;

    @Override
    public PageData<GoodsListRespDto> pageQuery(Integer pageNum, Integer pageSize, GoodsPageQuery query) {


        LambdaQueryWrapper<GoodsEntity> lambdaQueryWrapper = buildQueryWrapper(query);

        lambdaQueryWrapper.orderByDesc(GoodsEntity::getCreateTime);
        SortDto sort = query.getSort();
        if (sort == null) {
            query.setSort(new SortDto(AppGoodsHomePageSortFieldEnum.TIME.name(), "DESC"));
        }
        buildQuerySort(sort, lambdaQueryWrapper);

        IPage<GoodsEntity> page = super.page(new Page<GoodsEntity>(pageNum, pageSize), lambdaQueryWrapper);
        List<GoodsListRespDto> respData = goodsTransfer.assembleGoodsListRespDto(page.getRecords());
        return PageDataUtils.build(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), respData);
    }

    private LambdaQueryWrapper<GoodsEntity> buildQueryWrapper(GoodsPageQuery query) {
        String shopId = query.getShopId();
        String searchKeyword = query.getSearchKeyword();
        String goodsTypeCode = query.getGoodsTypeCode();
        String categoryId = query.getCategoryId();

        List<String> categoryIdsList = new ArrayList<>();
        if (StringUtils.isNotEmpty(categoryId)) {
            LambdaQueryWrapper<CategoryEntity> categoryQuery = Wrappers.<CategoryEntity>lambdaQuery().likeRight(CategoryEntity::getIdPath, categoryId);
            List<CategoryEntity> list = categoryMapper.selectList(categoryQuery);
            categoryIdsList = list.stream().map(CategoryEntity::getId).collect(Collectors.toList());
        }

        List<String> goodsTypeCodeList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(goodsTypeCode)) {
            goodsTypeCodeList.add(goodsTypeCode);
        } else {
            goodsTypeCodeList = Lists.newArrayList(GoodsTypeEnum.CUSTOM_MADE.name(), PRE_SELL.name(), GoodsTypeEnum.SELL.name());
        }

        LambdaQueryWrapper<GoodsEntity> lambdaQueryWrapper = new LambdaQueryWrapper<GoodsEntity>().eq(GoodsEntity::getStatus, GoodsStatusEnum.UP.name()).in(GoodsEntity::getGoodsTypeCode, goodsTypeCodeList).eq(StringUtils.isNotEmpty(shopId), GoodsEntity::getShopId, shopId).in(!CollectionUtils.isEmpty(categoryIdsList), GoodsEntity::getCategoryId, categoryIdsList).notIn(!CollectionUtils.isEmpty(query.getExcludeIds()), GoodsEntity::getId, query.getExcludeIds());

        if (PRI.name().equalsIgnoreCase(goodsTypeCode)) {
            lambdaQueryWrapper.eq(GoodsEntity::getBuyerId, "NONE");
        }

        if (StringUtils.isNotEmpty(searchKeyword)) {
            lambdaQueryWrapper.and(wrapper -> wrapper.like(GoodsEntity::getTitle, searchKeyword).or().like(GoodsEntity::getSubTitle, searchKeyword));
        }
        return lambdaQueryWrapper;
    }

    private void buildQuerySort(SortDto sortDto, LambdaQueryWrapper<GoodsEntity> lambdaQueryWrapper) {
        // 排序
        if (sortDto == null) {
            return;
        }
        String key = sortDto.getKey();
        boolean isASE = SortOrderEnum.ASC.name().equalsIgnoreCase(sortDto.getOrder());
        if (AppGoodsHomePageSortFieldEnum.SALE.name().equalsIgnoreCase(key)) {
            lambdaQueryWrapper.orderBy(true, isASE, GoodsEntity::getSaleNumber, GoodsEntity::getCreateTime);
        } else if (AppGoodsHomePageSortFieldEnum.PRICE.name().equalsIgnoreCase(key)) {
            lambdaQueryWrapper.orderBy(true, isASE, GoodsEntity::getMinSellingPrice, GoodsEntity::getCreateTime);
        } else if (AppGoodsHomePageSortFieldEnum.TIME.name().equalsIgnoreCase(key)) {
            lambdaQueryWrapper.orderBy(true, isASE, GoodsEntity::getCreateTime);
        }
    }

    @Override
    public PageData<GoodsListRespDto> shopHomePage(Integer pageNum, Integer pageSize, GoodsPageQuery query) {
        String shopId = query.getShopId();

        LambdaQueryWrapper<GoodsEntity> lambdaQueryWrapper = buildQueryWrapper(query).orderByDesc(GoodsEntity::getIsTop, GoodsEntity::getSaleNumber, GoodsEntity::getCreateTime);

        IPage<GoodsEntity> page = super.page(new Page<GoodsEntity>(pageNum, pageSize), lambdaQueryWrapper);
        List<GoodsListRespDto> respData = goodsTransfer.assembleGoodsListRespDto(page.getRecords());
        return PageDataUtils.build(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), respData);
    }

    @Override
    public PageData<GoodsListRespDto> managePageQuery(Integer pageNum, Integer pageSize, GoodsManagePageQuery query) {
        SortDto sortDto = query.getSort();
        String searchKeyword = query.getSearchKeyword();
        LambdaQueryWrapper<GoodsEntity> lambdaQueryWrapper = Wrappers.<GoodsEntity>lambdaQuery().eq(StringUtils.isNotEmpty(query.getUserId()), GoodsEntity::getUserId, query.getUserId()).eq(StringUtils.isNotEmpty(query.getId()), GoodsEntity::getId, query.getId()).eq(StringUtils.isNotEmpty(query.getShopId()), GoodsEntity::getShopId, query.getShopId()).eq(StringUtils.isNotEmpty(query.getCategoryId()), GoodsEntity::getCategoryId, query.getCategoryId()).eq(StringUtils.isNotEmpty(query.getStatus()), GoodsEntity::getStatus, query.getStatus()).like(StringUtils.isNotEmpty(query.getTitle()), GoodsEntity::getTitle, query.getTitle());

        if (StringUtils.isNotEmpty(searchKeyword)) {
            lambdaQueryWrapper.and(wrapper -> wrapper.like(GoodsEntity::getTitle, searchKeyword).or().like(GoodsEntity::getSubTitle, searchKeyword));
        }

        // 排序
        if (sortDto == null) {
            lambdaQueryWrapper.orderByDesc(GoodsEntity::getSaleNumber, GoodsEntity::getCreateTime);
        } else {
            String key = sortDto.getKey();
            boolean isASE = SortOrderEnum.ASC.name().equalsIgnoreCase(sortDto.getOrder());
            if (AppGoodsManagePageSortFiledEnum.SALE.name().equalsIgnoreCase(key)) {
                lambdaQueryWrapper.orderBy(true, isASE, GoodsEntity::getSaleNumber, GoodsEntity::getCreateTime);
                // 库存
            } else if (AppGoodsManagePageSortFiledEnum.STOCK.name().equalsIgnoreCase(key)) {
                lambdaQueryWrapper.orderBy(true, isASE, GoodsEntity::getMinSellingPrice, GoodsEntity::getCreateTime);
            } else if (AppGoodsManagePageSortFiledEnum.CREATE_TIME.name().equalsIgnoreCase(key)) {
                lambdaQueryWrapper.orderBy(true, isASE, GoodsEntity::getCreateTime);
            }
        }

        IPage<GoodsEntity> page = super.page(new Page<GoodsEntity>(pageNum, pageSize), lambdaQueryWrapper);
        List<GoodsListRespDto> respData = goodsTransfer.assembleGoodsListRespDto(page.getRecords());
        if (!CollectionUtils.isEmpty(respData)) {
            List<String> goodsIds = respData.stream().map(GoodsListRespDto::getId).collect(Collectors.toList());
            List<ShoppingCardPurchaseNumVo> shoppingCardPurchaseNumVos = shoppingCardApiClient.goodsPurchaseNum(goodsIds);
            Map<String, Integer> purchaseNumMap = shoppingCardPurchaseNumVos.stream().collect(Collectors.toMap(ShoppingCardPurchaseNumVo::getGoodsId, ShoppingCardPurchaseNumVo::getPurchaseNum));
            respData.forEach(e -> {
                CategoryEntity categoryEntity = categoryMapper.selectById(e.getCategoryId());
                e.setCategoryName(categoryEntity.getNamePath());
                e.setShopName(genericService.getGenericShopInfoByShopId(e.getShopId(), e.getShopType(), true, false).getShopName());
                e.setAuditStatus(GoodsAuditStatusEnum.PASS.name());
                e.setStatusDesc(Enum.valueOf(GoodsStatusEnum.class, e.getStatus()).getDesc());
                e.setPurchaseNumber(purchaseNumMap.getOrDefault(e.getId(), 0));
                e.setTotalStock(Optional.ofNullable(e.getTotalStock()).orElse(0));
                e.setSaleNumber(Optional.ofNullable(e.getSaleNumber()).orElse(0));
            });
        }
        return PageDataUtils.build(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), respData);
    }

    @Override
    public GoodsDetailRespDto detail(String goodsId, Consumer<GoodsEntity> c) {
        GoodsEntity entity = super.getById(goodsId);
        return Optional.ofNullable(entity).map(e -> {
            if (c != null) {
                c.accept(entity);
            }
            GoodsDetailRespDto dto = goodsTransfer.convertGoodsDetailRespDto(entity);
            populateExpress(entity, dto);
            // 填充属性数据
            populateAttr(entity, dto);
            // 填充SKU数据
            populateSku(entity, dto);
            // 计算距离活动开始
            calActivityDeadLine(entity, dto);
            // 是否允许购买
            calActivityIsBegin(entity, dto);
            // 活动是否结束
            calActivityIsEnd(entity, dto);
            return dto;
        }).orElse(null);
    }

    @Override
    public GoodsDetailRespDto manageDetail(GoodsManageQuery query) {
        LambdaQueryWrapper<GoodsEntity> goodsQuery = Wrappers.<GoodsEntity>lambdaQuery().eq(StringUtils.isNotEmpty(query.getUserId()), GoodsEntity::getUserId, query.getUserId()).eq(StringUtils.isNotEmpty(query.getShopId()), GoodsEntity::getShopId, query.getShopId()).eq(GoodsEntity::getId, query.getId());

        GoodsEntity entity = super.getOne(goodsQuery);
        return Optional.ofNullable(entity).map(e -> {
            GoodsDetailRespDto dto = goodsTransfer.convertGoodsDetailRespDto(entity);
            populateExpress(entity, dto);
            // 填充属性数据
            populateAttr(entity, dto);
            // 填充类目
            populateCategory(entity, dto);
            // 填充SKU数据
            populateSku(entity, dto);
            // 计算距离活动开始
            calActivityDeadLine(entity, dto);
            // 是否允许购买
            calActivityIsBegin(entity, dto);
            // 活动是否结束
            calActivityIsEnd(entity, dto);
            return dto;
        }).orElse(null);
    }

    private void populateCategory(GoodsEntity entity, GoodsDetailRespDto dto) {
        CategoryEntity categoryEntity = categoryMapper.selectById(entity.getCategoryId());
        dto.setCategoryName(categoryEntity.getName());
        dto.setCategoryIdPath(categoryEntity.getIdPath());
        dto.setCategoryNamePath(categoryEntity.getNamePath());
    }

    private void calActivityIsEnd(GoodsEntity entity, GoodsDetailRespDto dto) {
        if (PRE_SELL.name().equalsIgnoreCase(entity.getGoodsTypeCode())) {
            dto.setActivityEnd(TRUE.getValue().equals(entity.getActivityEnd()) || entity.getActivityEndTime().getTime() <= System.currentTimeMillis());
        }
    }

    private void populateExpress(GoodsEntity entity, GoodsDetailRespDto dto) {
        String expressType = dto.getExpressType();
        if (UNIFIED.name().equals(expressType)) {
            dto.setUnifiedExpress(JSON.parseObject(entity.getExpressTypeValue(), UnifiedExpressVo.class));
        } else if (!FREE.name().equals(expressType)) {
            FreightTemplateEntity freightTemplateEntity = freightTemplateMapper.selectById(entity.getExpressTypeValue());
            dto.setExpressTemplateId(entity.getExpressTypeValue());
            dto.setExpressTemplateName(freightTemplateEntity.getName());
            dto.setExpressTemplateType(freightTemplateEntity.getType());
        }
    }

    private void populateAttr(GoodsEntity entity, GoodsDetailRespDto dto) {
        dto.setAttributeList(goodsAttributeService.queryGoodsAttribute(entity.getId()));
    }

    private void populateSku(GoodsEntity entity, GoodsDetailRespDto dto) {
        List<SkuEntity> list = skuMapper.findByGoodsId(entity.getId(), true, false);
        dto.setSkuList(skuTransfer.convertSkuRespDto(list));
    }

    private void calActivityDeadLine(GoodsEntity entity, GoodsDetailRespDto dto) {
        if (PRE_SELL.name().equals(entity.getGoodsTypeCode())) {
            dto.setDistanceToActivitySt(System.currentTimeMillis() - entity.getActivityStartTime().getTime());
        }
    }

    private void calActivityIsBegin(GoodsEntity entity, GoodsDetailRespDto dto) {
        String goodsTypeCode = entity.getGoodsTypeCode();
        if (PRE_SELL.name().equals(goodsTypeCode)) {
            long now = System.currentTimeMillis();
            dto.setPurchasable(entity.getActivityStartTime().getTime() <= now && now <= entity.getActivityEndTime().getTime());
        } else {
            dto.setPurchasable(true);
        }
    }
}
