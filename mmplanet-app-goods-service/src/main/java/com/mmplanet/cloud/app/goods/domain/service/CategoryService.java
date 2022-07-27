package com.mmplanet.cloud.app.goods.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;

import java.util.List;

import com.mmplanet.cloud.app.goods.dto.CategoryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryPageQueryDto;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryEntity;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;

public interface CategoryService extends IService<CategoryEntity> {

    PageData<CategoryVo> pageQuery(SearchModel<CategoryPageQueryDto> dto);

    List<CategoryVo> listFor(CategoryDto dto);

    CategoryVo detail(String id);

    List<CategoryTreeVo> tree();

    Boolean removeById(String id, String opUserId);
}
