package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.domain.service.GoodsManageStatisticsService;
import com.mmplanet.cloud.app.goods.dto.GoodsManageStatisticsQueryDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsManageStatisticsEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.GoodsManageStatisticsMapper;
import com.mmplanet.cloud.app.goods.vo.GoodsManageStatisticsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/23 11:44 <br>
 * @Author: niujiao
 */
@Service
public class GoodsManageStatisticsServiceImpl extends ServiceImpl<GoodsManageStatisticsMapper, GoodsManageStatisticsEntity> implements GoodsManageStatisticsService {
    @Override
    public GoodsManageStatisticsVo manageStatistics(GoodsManageStatisticsQueryDto requestBean) {

        String userId = requestBean.getUserId();
        String shopId = requestBean.getShopId();

        LambdaQueryWrapper<GoodsManageStatisticsEntity> queryWrapper = new LambdaQueryWrapper<GoodsManageStatisticsEntity>()
                .eq(GoodsManageStatisticsEntity::getUserId, userId)
                .eq(GoodsManageStatisticsEntity::getShopId, shopId);
        GoodsManageStatisticsEntity entity = getOne(queryWrapper);

        GoodsManageStatisticsVo vo = new GoodsManageStatisticsVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
