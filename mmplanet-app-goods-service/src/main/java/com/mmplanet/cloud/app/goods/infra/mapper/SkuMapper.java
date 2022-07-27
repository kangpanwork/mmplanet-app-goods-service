package com.mmplanet.cloud.app.goods.infra.mapper;

import com.mmplanet.cloud.app.goods.infra.entity.SkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 商品sku信息表 Mapper 接口
 * </p>
 *
 * @author jacky
 * @since 2022-05-17
 */
public interface SkuMapper extends BaseMapper<SkuEntity> {

    @Update("update chh_sku set stock = stock - #{num} where id = #{skuId} and stock - #{num} >= 0")
    int stockReduce(String skuId, int num);

    @Update("update chh_sku set stock = stock + #{num} where id =#{skuId} and  stock >= 0")
    int stockAdd(String skuId, Integer num);

    List<SkuEntity> findByGoodsId(@Param("goodsId") String goodsId, @Param("isPurchase") Boolean isPurchase, @Param("deleted") Boolean deleted);

    List<SkuEntity> get(@Param("skuIds") List<String> skuIds);
}
