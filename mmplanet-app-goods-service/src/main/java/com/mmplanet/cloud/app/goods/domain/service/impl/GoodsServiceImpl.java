package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.goods.domain.service.CategoryService;
import com.mmplanet.cloud.app.goods.domain.service.GenericService;
import com.mmplanet.cloud.app.goods.domain.service.GoodsService;
import com.mmplanet.cloud.app.goods.domain.transfer.GoodsTransfer;
import com.mmplanet.cloud.app.goods.dto.GoodsManageStatisticsQueryDto;
import com.mmplanet.cloud.app.goods.dto.GoodsManageTopDto;
import com.mmplanet.cloud.app.goods.dto.GoodsUpAndDownDto;
import com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.infra.integration.ShoppingCardApiClient;
import com.mmplanet.cloud.app.goods.infra.mapper.GoodsMapper;
import com.mmplanet.cloud.app.goods.vo.GoodsManageStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.common.code.BaseCode.FAIL;
import static com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum.PRE_SELL;
import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;


@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {
    @Autowired
    private GoodsTransfer goodsTransfer;

    @Autowired
    private ShoppingCardApiClient shoppingCardApiClient;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GenericService genericService;

    @Override
    public GoodsEntity getById(String id) {
        List<GoodsEntity> goodsEntities = listByIds(Lists.newArrayList(id));
        return CollectionUtils.isEmpty(goodsEntities) ? null : goodsEntities.get(0);
    }

    @Override
    public List<GoodsEntity> listByIds(List<String> ids) {
        List<GoodsEntity> entities = getBaseMapper().listByIds(ids);
        if (CollectionUtils.isEmpty(entities)) {
            return entities;
        }
        entities.forEach(e -> {
            if (Objects.equals(e.getDeleted(), TRUE.getValue())) {
                e.setStatus(GoodsStatusEnum.DELETE.name());
            }
        });
        return entities;
    }

    @Override
    public Boolean manageUpAndDown(GoodsUpAndDownDto request) {
        String upOrDown = request.getUpOrDown();
        List<GoodsEntity> goodsEntities = isExist(request.getIds(), null);
        goodsEntities.forEach(e -> {
            String goodsTypeCode = e.getGoodsTypeCode();
            if (PRE_SELL.name().equals(goodsTypeCode)
                    && (TRUE.getValue().equals(e.getActivityEnd()) || e.getActivityEndTime().getTime() <= System.currentTimeMillis())
                    && "UP".equals(upOrDown)) {
                throw new BaseException(FAIL, "预售商品活动结束不允许重新上架！");
            }
            e.setStatus(upOrDown);
            e.setUpdateTime(new Date());
            e.setUpdateUser(request.getOpUserId());
        });
        return super.updateBatchById(goodsEntities);
    }

    private List<GoodsEntity> isExist(List<String> ids, String userId) {
        ids = ids.stream().distinct().collect(Collectors.toList());

        LambdaQueryWrapper<GoodsEntity> lambdaQueryWrapper = new LambdaQueryWrapper<GoodsEntity>()
                .eq(StringUtils.isNotEmpty(userId), GoodsEntity::getUserId, userId)
                .in(GoodsEntity::getId, ids);
        List<GoodsEntity> goodsEntities = super.list(lambdaQueryWrapper);
        if (ids.size() != goodsEntities.size()) {
            log.warn("商品id标识非法，param={}", JSON.toJSONString(ids));
            throw new BaseException(BaseCode.REQUIRED_REQUEST_BODY);
        }
        return goodsEntities;
    }

    @Override
    public Boolean removeByIds(List<String> ids, String opUserId) {
        List<GoodsEntity> goodsEntities = isExist(ids, null);
        goodsEntities.forEach(e -> {
            UpdateWrapper<GoodsEntity> updateWrapper =
                    new UpdateWrapper<GoodsEntity>()
                            .set("update_user", opUserId)
                            .set("update_time", new Date())
                            .set("deleted", TRUE.getValue())
                            .eq("id", e.getId());
            super.update(updateWrapper);
        });
        return true;
    }

    @Override
    public Boolean manageTop(GoodsManageTopDto request) {
        List<GoodsEntity> goodsEntities = isExist(request.getIds(), null);
        goodsEntities.forEach(e -> {
            e.setUpdateUser(request.getOpUserId());
            e.setUpdateTime(new Date());
            e.setIsTop(request.getTop());
        });
        return updateBatchById(goodsEntities);
    }

    @Override
    public GoodsManageStatisticsVo manageStatistics(GoodsManageStatisticsQueryDto requestBean) {
        List<Map<String, Integer>> list = getBaseMapper().manageStatistics(requestBean.getShopId(), requestBean.getUserId());
        // list to map
        Map<String, Integer> map = new HashMap<String, Integer>();
        list.forEach(map::putAll);
        GoodsManageStatisticsVo vo = new GoodsManageStatisticsVo();
        vo.setToAuditNum(map.getOrDefault(GoodsAuditStatusEnum.TO_AUDIT.name(), 0));
        vo.setToAuditNum(map.getOrDefault(GoodsAuditStatusEnum.PASS.name(), 0));
        vo.setToAuditNum(map.getOrDefault(GoodsAuditStatusEnum.REJECT.name(), 0));
        return vo;
    }

    @Override
    public boolean reduceSaleNum(String goodsId, Integer num) {
        return getBaseMapper().reduceSaleNum(goodsId, num) == 1;
    }

    @Override
    public boolean addSaleNum(String goodsId, Integer num) {
        return getBaseMapper().addSaleNum(goodsId, num) == 1;
    }

    @Override
    public boolean reduceStockNum(String goodsId, Integer num) {
        return getBaseMapper().reduceStockNum(goodsId, num) == 1;
    }

    @Override
    public boolean addStockNum(String goodsId, Integer num) {
        return getBaseMapper().addStockNum(goodsId, num) == 1;
    }
}