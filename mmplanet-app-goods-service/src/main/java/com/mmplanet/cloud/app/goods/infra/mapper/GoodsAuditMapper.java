package com.mmplanet.cloud.app.goods.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAuditEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品审核表 Mapper 接口
 * </p>
 *
 * @author niujiao
 * @since 2022-05-26
 */
public interface GoodsAuditMapper extends BaseMapper<GoodsAuditEntity> {

    Page<Map<String, Object>> queryAuditPage(IPage<?> page,@Param("query") Map<String, Object> queryParam);
}
