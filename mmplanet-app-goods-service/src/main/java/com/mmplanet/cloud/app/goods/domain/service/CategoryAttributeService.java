package com.mmplanet.cloud.app.goods.domain.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import java.util.List;

import com.mmplanet.cloud.app.goods.dto.CategoryAttributeDto;
import com.mmplanet.cloud.app.goods.dto.CategoryAttributePageQueryDto;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryAttributeEntity;
import com.mmplanet.cloud.app.goods.vo.CategoryAttributeVo;

public interface CategoryAttributeService extends IService<CategoryAttributeEntity> {

    Boolean removeById(Integer id, String opUserId);
}
