package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.dao.util.PageUtil;
import com.mmplanet.cloud.app.goods.dto.GoodsAttributeValueDto;
import com.mmplanet.cloud.app.goods.dto.GoodsAttributeValuePageQueryDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeValueEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.GoodsAttributeValueMapper;
import com.mmplanet.cloud.app.goods.domain.service.GoodsAttributeValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.domain.transfer.GoodsAttributeValueTransfer;
import com.mmplanet.cloud.app.goods.vo.GoodsAttributeValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class GoodsAttributeValueServiceImpl  extends ServiceImpl<GoodsAttributeValueMapper, GoodsAttributeValueEntity> implements GoodsAttributeValueService{
        @Autowired
        private GoodsAttributeValueTransfer goodsAttributeValueTransfer;
        @Override
        public PageData<GoodsAttributeValueVo> pageQuery(SearchModel<GoodsAttributeValuePageQueryDto> searchModel) {
            GoodsAttributeValuePageQueryDto dto = searchModel.getModel();
            Page<GoodsAttributeValueVo> page = new Page<>(searchModel.getPage().getPageNum(), searchModel.getPage().getPageSize());
            List<GoodsAttributeValueVo> list=new ArrayList<>();
            page.setRecords(list);
            return PageUtil.pageToPageData(page);
        }


        @Override
        public GoodsAttributeValueVo detail(String id) {
                GoodsAttributeValueEntity entity = this.getById(id);
                GoodsAttributeValueVo vo = new GoodsAttributeValueVo();
                BeanUtils.copyProperties(entity,vo);
                return vo;
        }

}



