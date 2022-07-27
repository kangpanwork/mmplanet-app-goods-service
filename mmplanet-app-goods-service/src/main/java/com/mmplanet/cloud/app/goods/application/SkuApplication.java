package com.mmplanet.cloud.app.goods.application;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.dto.BaseIdsDto;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.util.JsonHelper;
import com.mmplanet.cloud.app.goods.domain.event.SkuSellOutEvent;
import com.mmplanet.cloud.app.goods.domain.service.GoodsService;
import com.mmplanet.cloud.app.goods.domain.service.SkuService;
import com.mmplanet.cloud.app.goods.domain.service.SkuStockReduceService;
import com.mmplanet.cloud.app.goods.domain.transfer.SkuTransfer;
import com.mmplanet.cloud.app.goods.dto.*;
import com.mmplanet.cloud.app.goods.enums.GoodsStatusEnum;
import com.mmplanet.cloud.app.goods.enums.StockReduceTypeEnum;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.infra.entity.SkuEntity;
import com.mmplanet.cloud.app.goods.infra.util.SpringApplicationContentUtils;
import com.mmplanet.cloud.app.goods.vo.FullSkuVo;
import com.mmplanet.cloud.app.goods.vo.GoodsDetailSkuVo;
import com.mmplanet.cloud.app.goods.vo.ShoppingCardSkuVo;
import com.mmplanet.cloud.app.goods.vo.SkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.goods.enums.GoodsTypeEnum.PRE_SELL;
import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;

/**
 * 商品sku信息表 应用层
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */

@Component
public class SkuApplication {

    @Autowired
    private SkuService skuService;

    @Resource
    private SkuStockReduceService skuStockReduceService;

    @Resource
    private GoodsService goodsService;

    @Autowired
    private SkuTransfer skuTransfer;

    /**
     * 查询数据
     */
    public List<ShoppingCardSkuVo> shoppingCardSkuList(ShoppingCardSkusDto shoppingCardSkusDto) {

        List<SkuEntity> result = skuService.findByGoodsId(shoppingCardSkusDto.getGoodsId());
        List<ShoppingCardSkuVo> rtnResult = null;
        if (CollectionUtil.isNotEmpty(result)) {
            rtnResult = JsonHelper.jsonToList(JsonHelper.objectToJson(result), ShoppingCardSkuVo.class);
        }
        String selectSkuId = shoppingCardSkusDto.getSkuId();
        if (StringUtils.isNotEmpty(selectSkuId) && CollectionUtil.isNotEmpty(rtnResult)) {
            for (ShoppingCardSkuVo item : rtnResult) {
                if (selectSkuId.equalsIgnoreCase(item.getId())) {
                    item.setSelected(true);
                    break;
                }
            }
        }
        return rtnResult;
    }

    /**
     * 根据id查询
     */
    public SkuVo detail(String id) {
        SkuEntity skuEntity = skuService.getById(id);
        return skuTransfer.assembleSkuVo(skuEntity);
    }

    public GoodsDetailSkuVo goodsSkuDetail(GoodsDetailSkuDto dto) {
        SkuEntity skuEntity = skuService.getById(dto.getSkuId());
        return skuTransfer.assembleGoodsDetailSkuVo(skuEntity);
    }

    public List<FullSkuVo> fullDetail(BaseIdsDto dto) {
        List<SkuEntity> skuEntities = skuService.get(dto.getIds());
        if (CollectionUtils.isEmpty(skuEntities)) {
            return null;
        }
        return skuEntities.stream().map(e -> {
            FullSkuVo fullSkuVo = skuTransfer.assembleFullSkuVo(e);
            GoodsEntity goodsEntity = goodsService.getById(e.getGoodsId());
            fullSkuVo.setGoodsId(goodsEntity.getId());
            fullSkuVo.setUserId(goodsEntity.getUserId());
            fullSkuVo.setShopId(goodsEntity.getShopId());
            fullSkuVo.setGoodsSmallImage(goodsEntity.getSmallImage());
            fullSkuVo.setTitle(goodsEntity.getTitle());
            fullSkuVo.setSubTitle(goodsEntity.getSubTitle());
            fullSkuVo.setGoodsTypeCode(goodsEntity.getGoodsTypeCode());
            fullSkuVo.setGoodsTypeName(goodsEntity.getGoodsTypeName());
            fullSkuVo.setActivityStartTime(goodsEntity.getActivityStartTime());
            long now = System.currentTimeMillis();
            if (PRE_SELL.name().equalsIgnoreCase(goodsEntity.getGoodsTypeCode())) {
                fullSkuVo.setActivityEnd(TRUE.getValue().equals(goodsEntity.getActivityEnd()) || goodsEntity.getActivityEndTime().getTime() <= now);
            }
            fullSkuVo.setPurchasable(!PRE_SELL.name().equals(goodsEntity.getGoodsTypeCode()) ||
                    goodsEntity.getActivityStartTime().getTime() <= now && now <= goodsEntity.getActivityEndTime().getTime());
            fullSkuVo.setActivityEndTime(goodsEntity.getActivityEndTime());
            fullSkuVo.setAttribute(goodsEntity.getAttribute());
            fullSkuVo.setAttributeDesc(goodsEntity.getAttributeDesc());
            fullSkuVo.setStockReduce(goodsEntity.getStockReduce());
            fullSkuVo.setUnit(goodsEntity.getUnit());
            fullSkuVo.setStatus(e.getDeleted() == 1 ? GoodsStatusEnum.DELETE.name() : goodsEntity.getStatus());
            fullSkuVo.setGoodsStatus(goodsEntity.getStatus());
            fullSkuVo.setExpressType(goodsEntity.getExpressType());
            fullSkuVo.setExpressTypeValue(goodsEntity.getExpressTypeValue());
            return fullSkuVo;
        }).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean stockReduce(SkuStockReduceDto data) {
        boolean result = false;
        // 校验sku是否存在
        String skuId = data.getSkuId();
        SkuEntity skuEntity = skuService.getById(skuId);
        Optional.ofNullable(skuEntity).orElseThrow(() -> new BaseException(BaseCode.VALID_ERROR, "skuId=" + skuId + " 不存在"));
        if (skuStockReduceService.save(data)) {
            String type = data.getType();
            Integer num = data.getNum();
            if (StockReduceTypeEnum.REDUCE.name().equals(type)) {
                result = skuService.reduceStock(skuId, num)
                        && goodsService.reduceStockNum(skuEntity.getGoodsId(), num)
                        && goodsService.addSaleNum(skuEntity.getGoodsId(), num);
            } else {
                result = skuService.addStock(skuId, num)
                        && goodsService.addStockNum(skuEntity.getGoodsId(), num)
                        && goodsService.reduceSaleNum(skuEntity.getGoodsId(), num);
            }
        }
        if (!result) {
            SpringApplicationContentUtils.getContext().publishEvent(new SkuSellOutEvent(this, skuId));
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean fastModify(FastModifySkuDto dto) {
        String goodsId = dto.getGoodsId();
        List<FastModifySkuDto.SkuItemDto> skuList = dto.getSkuList();

        LambdaQueryWrapper<GoodsEntity> query = Wrappers.<GoodsEntity>lambdaQuery().eq(GoodsEntity::getId, goodsId);
        int count = goodsService.count(query);
        if (count == 0) {
            throw new BaseException(BaseCode.FAIL, "商品不存在！");
        }


        List<SkuEntity> modifyList = new ArrayList<>();
        skuList.forEach(e -> {
            LambdaQueryWrapper<SkuEntity> skuQuery = Wrappers.<SkuEntity>lambdaQuery()
                    .eq(SkuEntity::getId, e.getId())
                    .eq(SkuEntity::getGoodsId, goodsId);
            SkuEntity skuEntity = skuService.getOne(skuQuery);
            Optional.ofNullable(skuEntity).orElseThrow(() -> new BaseException(BaseCode.FAIL, "SKU不存在！"));

            SkuEntity modifyEntity = new SkuEntity();
            modifyEntity.setId(skuEntity.getId());
            modifyEntity.setPrice(e.getPrice());
            modifyEntity.setStock(e.getStock());
            modifyEntity.setUpdateTime(new Date());
            modifyEntity.setUpdateUser(dto.getOpUserId());
            modifyList.add(modifyEntity);
        });
        skuService.saveOrUpdateBatch(modifyList);
        return true;
    }

    public List<SkuVo> findSkuByParam(FindSkuByParamQueryDto queryDto) {
        List<SkuEntity> skuEntities = skuService.findByGoodsId(queryDto.getGoodsId());
        if (!CollectionUtils.isEmpty(skuEntities)) {
            return skuEntities.stream().map(e -> skuTransfer.assembleSkuVo(e)).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
