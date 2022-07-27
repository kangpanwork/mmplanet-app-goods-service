package com.mmplanet.cloud.app.goods.domain.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.common.util.UUIDHelper;
import com.mmplanet.cloud.app.dao.util.PageUtil;
import com.mmplanet.cloud.app.goods.domain.service.CategoryService;
import com.mmplanet.cloud.app.goods.domain.transfer.CategoryTransfer;
import com.mmplanet.cloud.app.goods.dto.CategoryDto;
import com.mmplanet.cloud.app.goods.dto.CategoryPageQueryDto;
import com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.CategoryMapper;
import com.mmplanet.cloud.app.goods.infra.util.PageDataUtils;
import com.mmplanet.cloud.app.goods.vo.CategoryTreeVo;
import com.mmplanet.cloud.app.goods.vo.CategoryVo;
import com.mmplanet.cloud.app.goods.vo.OpsAttributeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {
    @Autowired
    private CategoryTransfer categoryTransfer;

    @Override
    public PageData<CategoryVo> pageQuery(SearchModel<CategoryPageQueryDto> searchModel) {


        Integer pageNum = searchModel.getPage().getPageNum();
        Integer pageSize = searchModel.getPage().getPageSize();

        // 查询参数
        CategoryPageQueryDto dto = searchModel.getModel();
        String pid = dto.getPid();

        Page<CategoryEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CategoryEntity> queryWrapper = new LambdaQueryWrapper<CategoryEntity>().eq(CategoryEntity::getPid, pid)
                .orderByDesc(CategoryEntity::getCreateTime);

        IPage<CategoryEntity> resultPage = page(page, queryWrapper);

        return PageDataUtils.<CategoryVo>build(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal(), resultPage.getPages(), categoryTransfer.listPo2listVo(resultPage.getRecords()));
    }

    @Override
    public boolean save(CategoryEntity entity) {
        String id = entity.getId();
        String pid = entity.getPid();
        if (!StringUtils.isEmpty(id)) {
            return modify(entity);
        }

        if (StringUtils.isEmpty(id)) {
            entity.setId(UUIDHelper.getUUID());
            entity.setIdPath(String.join("/", entity.getId()));
            entity.setNamePath(String.join("/", entity.getName()));
        }
        CategoryEntity pEntity = null;
        if (StringUtils.isNotEmpty(pid) && !"0".equals(pid)) {
            pEntity = getById(pid);
            entity.setIdPath(String.join("/", pEntity.getIdPath(), entity.getId()));
            entity.setNamePath(String.join("/", pEntity.getNamePath(), entity.getName()));
        }

        if (pEntity != null) {
            pEntity.setHasChildren(TRUE.getValue());
            getBaseMapper().updateById(pEntity);
        }

        return getBaseMapper().insert(entity) == 1;
    }

    private boolean modify(CategoryEntity entity) {
        String categoryId = entity.getId();
        CategoryEntity categoryEntity = this.getById(categoryId);
        Optional.ofNullable(categoryEntity).orElseThrow(() -> new BaseException(BaseCode.FAIL, "属性【" + categoryId + "】不存在！"));
        CategoryEntity pEntity = getById(categoryEntity.getPid());
        if(pEntity != null){
            entity.setNamePath(String.join(",", pEntity.getNamePath(), entity.getName()));
        }
        entity.setLastUpdateTime(new Date());
        return getBaseMapper().updateById(entity) == 1;
    }

    @Override
    public List<CategoryVo> listFor(CategoryDto caseParametersDto) {
        LambdaQueryWrapper<CategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<CategoryEntity> list = this.list(lambdaQueryWrapper);
        List<CategoryVo> vos = categoryTransfer.listPo2listVo(list);
        return vos;
    }

    @Override
    public CategoryVo detail(String id) {
        CategoryEntity entity = this.getById(id);
        CategoryVo vo = new CategoryVo();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public List<CategoryTreeVo> tree() {
        List<CategoryTreeVo> dtoList = categoryTransfer.assembleCategoryTreeVo(list());
        if (CollectionUtils.isEmpty(dtoList)) {
            return null;
        }
        // 构建类目树
        List<CategoryTreeVo> categoryTreeVos = dtoList.stream()
                .filter(e -> e.getPid().equals("0"))
                .peek((e) -> e.setChildren(getChildren(e, dtoList)))
                .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .collect(Collectors.toList());
        return categoryTreeVos;
    }

    @Override
    public Boolean removeById(String id, String opUserId) {
        UpdateWrapper<CategoryEntity> updateWrapper =
                new UpdateWrapper<CategoryEntity>()
                        .set("update_user", opUserId)
                        .set("update_time", new Date())
                        .set("deleted", TRUE.getValue())
                        .eq("id", id);
        return update(updateWrapper);
    }

    private List<CategoryTreeVo> getChildren(CategoryTreeVo root, List<CategoryTreeVo> all) {
        List<CategoryTreeVo> children = all.stream()
                .filter(e -> e.getPid().equals(root.getId()))
                .peek(e -> {
                    //1、找到子菜单(递归)
                    e.setChildren(getChildren(e, all));
                }).sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .collect(Collectors.toList());
        return children;
    }
}



