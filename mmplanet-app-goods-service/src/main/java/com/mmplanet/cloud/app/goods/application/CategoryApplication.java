package com.mmplanet.cloud.app.goods.application;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.goods.domain.service.*;
import com.mmplanet.cloud.app.goods.domain.transfer.AttributeTransfer;
import com.mmplanet.cloud.app.goods.domain.transfer.CategoryTransfer;
import com.mmplanet.cloud.app.goods.dto.CategoryAttributeQueryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryPageQueryDto;
import com.mmplanet.cloud.app.goods.dto.DeleteCategoryDto;
import com.mmplanet.cloud.app.goods.enums.EnableOrDisEnableEnum;
import com.mmplanet.cloud.app.goods.infra.config.GlobalConstant;
import com.mmplanet.cloud.app.goods.infra.entity.*;
import com.mmplanet.cloud.app.goods.vo.AttributeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品目录表 应用层
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Component
public class CategoryApplication {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryAttributeService categoryAttributeService;

    @Autowired
    private GoodsAuditService goodsAuditService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AttributeTransfer attributeTransfer;

    @Autowired
    private CategoryTransfer categoryTransfer;

    /**
     * 获取类目属性
     *
     * @param queryDto
     * @return
     */
    public List<AttributeVo> queryCategoryAttrs(CategoryAttributeQueryDto queryDto) {

        List<AttributeVo> ret = new ArrayList<AttributeVo>();

        String categoryId = queryDto.getCategoryId();
        CategoryEntity categoryEntity = categoryService.getById(categoryId);
        if (categoryEntity == null) {
            return ret;
        }

        List<String> categoryIdsList = new ArrayList<>();
        String idPath = categoryEntity.getIdPath();
        if (StringUtils.isNotEmpty(idPath)) {
            String[] categoryIds = idPath.split(GlobalConstant.BACKSLASH);
            categoryIdsList.addAll(Arrays.stream(categoryIds).collect(Collectors.toList()));
        } else {
            categoryIdsList.add(categoryEntity.getId());
        }

        LambdaQueryWrapper<CategoryAttributeEntity> categoryAttrQuery = Wrappers.<CategoryAttributeEntity>lambdaQuery().in(CategoryAttributeEntity::getCategoryId, categoryIdsList);

        List<CategoryAttributeEntity> list = categoryAttributeService.list(categoryAttrQuery);
        if (CollectionUtils.isEmpty(list)) {
            return ret;
        }

        Set<String> attrIdSet = list.stream().map(CategoryAttributeEntity::getAttributeId).collect(Collectors.toSet());

        LambdaQueryWrapper<AttributeEntity> attrQuery = Wrappers.<AttributeEntity>lambdaQuery().in(AttributeEntity::getId, attrIdSet);
        List<AttributeEntity> attributeEntities = attributeService.list(attrQuery);
        return attributeTransfer.assembleAttributeVo(attributeEntities);
    }

    /**
     * 查询分页数据
     */
    public PageData<CategoryVo> pageQuery(SearchModel<CategoryPageQueryDto> searchModel) {
        PageData<CategoryVo> result = categoryService.pageQuery(searchModel);
        return result;
    }

    /**
     * 查询数据
     */
    public List<CategoryVo> listFor(CategoryDto dto) {
        List<CategoryVo> result = categoryService.listFor(dto);
        return result;
    }

    /**
     * 根据id查询
     */
    public CategoryVo detail(String id) {
        CategoryVo result = categoryService.detail(id);
        return result;
    }

    /**
     * 新增或者修改
     */
    public Boolean save(CategoryDto dto) {
        String categoryId = dto.getId();
        CategoryEntity entity = categoryTransfer.toModel(dto);
        if (StringUtils.isEmpty(categoryId)) {
            entity.setCreateUser(dto.getOpUserId());
            entity.setCreateTime(new Date());
            entity.setStatus(EnableOrDisEnableEnum.ENABLE.name());
        } else {
            entity.setPid(null);
            entity.setUpdateUser(dto.getOpUserId());
            entity.setLastUpdateTime(new Date());
        }

        return categoryService.save(entity);
    }

    public List<CategoryTreeVo> tree() {
        return categoryService.tree();
    }

    public Boolean delete(DeleteCategoryDto dto) {

        String id = dto.getId();
        LambdaQueryWrapper<CategoryEntity> categoryQuery = Wrappers.<CategoryEntity>lambdaQuery()
                .eq(CategoryEntity::getId, id);
        CategoryEntity categoryEntity = categoryService.getOne(categoryQuery);
        if (categoryEntity == null) {
            throw new BaseException(BaseCode.FAIL, "类目ID不存在");
        }

        // 判断是否存在下级节点
        categoryQuery = Wrappers.<CategoryEntity>lambdaQuery()
                .ne(CategoryEntity::getId, id)
                .likeRight(CategoryEntity::getIdPath, categoryEntity.getIdPath());
        if (categoryService.count(categoryQuery) != 0) {
            throw new BaseException(BaseCode.FAIL, "该类目存在子类目，不允许删除！");
        }
        // 是否存在商品审核
        LambdaQueryWrapper<GoodsAuditEntity> goodsAuditQuery = Wrappers.<GoodsAuditEntity>lambdaQuery().
                eq(GoodsAuditEntity::getCategoryId, id);
        int goodsAuditCount = goodsAuditService.count(goodsAuditQuery);
        if (goodsAuditCount != 0) {
            throw new BaseException(BaseCode.FAIL, "该类目存在商品，不允许删除！");
        }
        // 是否存在商品
        LambdaQueryWrapper<GoodsEntity> goodsQuery = Wrappers.<GoodsEntity>lambdaQuery().
                eq(GoodsEntity::getCategoryId, id);
        int count = goodsService.count(goodsQuery);
        if (count == 0) {
            return categoryService.removeById(id, dto.getOpUserId());
        }
        throw new BaseException(BaseCode.FAIL, "该类目存在商品，不允许删除！");
    }
}
