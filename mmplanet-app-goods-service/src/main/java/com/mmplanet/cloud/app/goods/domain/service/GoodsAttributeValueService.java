package com.mmplanet.cloud.app.goods.domain.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import java.util.List;

import com.mmplanet.cloud.app.goods.dto.GoodsAttributeValueDto;
import com.mmplanet.cloud.app.goods.dto.GoodsAttributeValuePageQueryDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAttributeValueEntity;
import com.mmplanet.cloud.app.goods.vo.GoodsAttributeValueVo;

public interface GoodsAttributeValueService extends IService<GoodsAttributeValueEntity> {

    PageData<GoodsAttributeValueVo> pageQuery(SearchModel<GoodsAttributeValuePageQueryDto> dto);

    GoodsAttributeValueVo detail(String id);

}
