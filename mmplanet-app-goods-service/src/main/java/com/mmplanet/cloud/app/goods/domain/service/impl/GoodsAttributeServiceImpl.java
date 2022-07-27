package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsAttributeRespDto;
import com.mmplanet.cloud.app.goods.domain.service.GoodsAttributeService;
import com.mmplanet.cloud.app.goods.dto.GoodsDetailAttributeDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeValueEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.GoodsAttributeMapper;
import com.mmplanet.cloud.app.goods.infra.mapper.GoodsAttributeValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttributeEntity> implements GoodsAttributeService {

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Autowired
    private GoodsAttributeValueMapper goodsAttributeValueMapper;

    @Override
    public void saveGoodsAttribute(GoodsEntity goodsEntity, List<GoodsDetailAttributeDto> attributeList) {
        for (GoodsDetailAttributeDto attributeDto : attributeList) {
            // 属性
            GoodsAttributeEntity attributeEntity = new GoodsAttributeEntity();
            attributeEntity.setSn(attributeDto.getSn());
            attributeEntity.setGoodsId(goodsEntity.getId());
            attributeEntity.setName(attributeDto.getAttrName());
            goodsAttributeMapper.insert(attributeEntity);

            // 属性值
            attributeDto.getAttrValues().forEach(e -> {
                GoodsAttributeValueEntity attributeValueEntity = new GoodsAttributeValueEntity();
                attributeValueEntity.setAttributeId(attributeEntity.getId());
                attributeValueEntity.setGoodsId(goodsEntity.getId());
                attributeValueEntity.setAttributeValue(e);
                goodsAttributeValueMapper.insert(attributeValueEntity);
            });
        }
    }

    @Override
    public List<GoodsAttributeRespDto> queryGoodsAttribute(String goodsId) {

        List<GoodsAttributeEntity> attrList = goodsAttributeMapper
                .selectList(Wrappers.<GoodsAttributeEntity>lambdaQuery().eq(GoodsAttributeEntity::getGoodsId, goodsId));

        List<GoodsAttributeValueEntity> attrValueList = goodsAttributeValueMapper
                .selectList(Wrappers.<GoodsAttributeValueEntity>lambdaQuery().eq(GoodsAttributeValueEntity::getGoodsId, goodsId));
        // 分组
        Map<String, List<GoodsAttributeValueEntity>> attrValueGroup =
                attrValueList.stream().collect(Collectors.groupingBy(GoodsAttributeValueEntity::getAttributeId));

        return attrList.stream().map(e -> {
            GoodsAttributeRespDto attrDto = new GoodsAttributeRespDto();
            attrDto.setAttrName(e.getName());
            attrDto.setSn(e.getSn());

            List<GoodsAttributeValueEntity> list = attrValueGroup.get(e.getId());
            attrDto.setAttrValues(CollectionUtils.isEmpty(list) ? null
                    : list.stream().map(GoodsAttributeValueEntity::getAttributeValue).collect(Collectors.toList()));
            return attrDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteGoodsAttribute(String goodsId) {

        goodsAttributeMapper.delete(Wrappers.<GoodsAttributeEntity>lambdaQuery().eq(GoodsAttributeEntity::getGoodsId, goodsId));
        goodsAttributeValueMapper.delete(Wrappers.<GoodsAttributeValueEntity>lambdaQuery().eq(GoodsAttributeValueEntity::getGoodsId, goodsId));
    }
}



