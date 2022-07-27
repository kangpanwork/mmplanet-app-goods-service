package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsDetailRespDto;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsListRespDto;
import com.mmplanet.cloud.app.goods.dto.GoodsDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Arrays.class})
public interface GoodsTransfer {

    List<GoodsListVo> assembleGoodsListVo(List<GoodsListRespDto> records);

    List<GoodsManageListVo> assembleGoodsManageListVo(List<GoodsListRespDto> records);

    List<GoodsManageListVo> assembleGoodsManageListVoV1(List<GoodsEntity> records);

    @Mapping(target = "images", expression = "java(Arrays.asList(entity.getImages().split(\",\")))")
    @Mapping(target = "activityEnd", ignore = true)
    GoodsDetailVo assembleGoodsDetailVo(GoodsEntity entity);

    @Mapping(target = "images", expression = "java(Arrays.asList(entity.getImages().split(\",\")))")
    @Mapping(target = "activityEnd", ignore = true)
    GoodsManageDetailVo assembleGoodsManageDetailVo(GoodsEntity entity);

    @Mapping(target = "images", expression = "java(Arrays.asList(entity.getImages().split(\",\")))")
    SimpleGoodsVo assembleSimpleGoodsVo(GoodsEntity entity);

    @Mapping(target = "images", ignore = true)
    GoodsEntity toModel(GoodsDto dto);

    List<GoodsListRespDto> assembleGoodsListRespDto(List<GoodsEntity> records);

    List<GoodsListVo> assembleGoodsListVoV1(List<GoodsEntity> records);

    @Mapping(target = "expressTypeValue", ignore = true)
    @Mapping(target = "shop", source = "respDto.shopRespDto")
    @Mapping(target = "comment", source = "respDto.commentRespDto")
    GoodsDetailVo assembleGoodsDetailVo(GoodsDetailRespDto respDto);

    @Mapping(target = "images", expression = "java(Arrays.asList(entity.getImages().split(\",\")))")
    @Mapping(target = "activityEnd", ignore = true)
    GoodsDetailRespDto convertGoodsDetailRespDto(GoodsEntity entity);

    @Mapping(target = "shop", source = "respDto.shopRespDto")
    GoodsManageDetailVo assembleGoodsManageDetailVo(GoodsDetailRespDto respDto);
}
