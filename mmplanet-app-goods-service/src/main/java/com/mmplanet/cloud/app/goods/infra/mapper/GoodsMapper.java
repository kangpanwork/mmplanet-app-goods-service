package com.mmplanet.cloud.app.goods.infra.mapper;

import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author jacky
 * @since 2022-05-17
 */
public interface GoodsMapper extends BaseMapper<GoodsEntity> {

    @Select("SELECT status,COUNT(*) FROM chh_goods WHERE shop_id =#{shopId} AND user_id  =#{userId} GROUP BY status" +
            " union " +
            "select 'PASS' as status,count(*) from chh_goods WHERE shop_id =#{shopId} AND user_id  =#{userId}")
    List<Map<String, Integer>> manageStatistics(@Param("shopId") String shopId, @Param("userId") String userId);

    @Update("update chh_goods set sale_number = sale_number - #{num} where id = #{goodsId} and sale_number - #{num} >= 0")
    int reduceSaleNum(@Param("goodsId") String goodsId, @Param("num") Integer num);

    @Update("update chh_goods set total_stock = total_stock - #{num} where id = #{goodsId} and total_stock - #{num} >= 0")
    int reduceStockNum(@Param("goodsId") String goodsId, @Param("num") Integer num);

    @Update("update chh_goods set total_stock = total_stock + #{num} where id = #{goodsId} and total_stock >= 0")
    int addStockNum(@Param("goodsId") String goodsId, @Param("num") Integer num);

    @Update("update chh_goods set sale_number = sale_number + #{num} where id = #{goodsId} and sale_number >= 0")
    int addSaleNum(@Param("goodsId") String goodsId, @Param("num") Integer num);

    List<GoodsEntity> listByIds(@Param("ids") List<String> ids);
}
