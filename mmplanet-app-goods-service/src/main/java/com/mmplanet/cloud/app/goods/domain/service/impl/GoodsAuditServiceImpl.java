package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.dao.util.PageUtil;
import com.mmplanet.cloud.app.goods.domain.service.CategoryService;
import com.mmplanet.cloud.app.goods.domain.service.GenericService;
import com.mmplanet.cloud.app.goods.domain.service.GoodsAuditService;
import com.mmplanet.cloud.app.goods.domain.transfer.GoodsAuditTransfer;
import com.mmplanet.cloud.app.goods.dto.GoodsDto;
import com.mmplanet.cloud.app.goods.dto.GoodsManageAuditPageQueryDto;
import com.mmplanet.cloud.app.goods.dto.SkuDto;
import com.mmplanet.cloud.app.goods.infra.entity.CategoryEntity;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAuditEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.GoodsAuditMapper;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum.AUDIT;
import static com.mmplanet.cloud.app.goods.enums.GoodsAuditStatusEnum.TO_AUDIT;
import static com.mmplanet.cloud.app.goods.enums.TureOrFalseEnum.TRUE;

/**
 * <p>
 * 商品审核表 服务实现类
 * </p>
 *
 * @author niujiao
 * @since 2022-05-26
 */
@Slf4j
@Service
public class GoodsAuditServiceImpl extends ServiceImpl<GoodsAuditMapper, GoodsAuditEntity> implements GoodsAuditService {

    @Autowired
    private GoodsAuditTransfer goodsAuditTransfer;

    @Autowired
    private GenericService genericService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageData<GoodsManageAuditListVo> page(SearchModel<GoodsManageAuditPageQueryDto> searchModel) {

        List<GoodsManageAuditListVo> resultList = new ArrayList<GoodsManageAuditListVo>();

        GoodsManageAuditPageQueryDto dto = searchModel.getModel();
        //SortDto sortDto = dto.getSort();

        String auditStatus = dto.getAuditStatus();
        String searchKeyword = dto.getSearchKeyword();
        LambdaQueryWrapper<GoodsAuditEntity> lambdaQueryWrapper = Wrappers.<GoodsAuditEntity>lambdaQuery()
                .eq(StringUtils.isNotEmpty(dto.getUserId()), GoodsAuditEntity::getUserId, dto.getUserId())
                .eq(StringUtils.isNotEmpty(dto.getGoodsId()), GoodsAuditEntity::getGoodsId, dto.getGoodsId())
                .eq(StringUtils.isNotEmpty(dto.getShopId()), GoodsAuditEntity::getShopId, dto.getShopId())
                .eq(StringUtils.isNotEmpty(dto.getCategoryId()), GoodsAuditEntity::getCategoryId, dto.getCategoryId())
                .like(StringUtils.isNotEmpty(dto.getTitle()), GoodsAuditEntity::getTitle, dto.getTitle())
                .like(StringUtils.isNotEmpty(searchKeyword), GoodsAuditEntity::getTitle, searchKeyword)
                .like(StringUtils.isNotEmpty(searchKeyword), GoodsAuditEntity::getSubTitle, searchKeyword)
                .orderByDesc(GoodsAuditEntity::getCreateTime);

        if (TO_AUDIT.name().equals(auditStatus)) {
            lambdaQueryWrapper.in(GoodsAuditEntity::getStatus, Lists.newArrayList(TO_AUDIT.name(), AUDIT.name()));
        } else {
            lambdaQueryWrapper.eq(GoodsAuditEntity::getStatus, auditStatus);
        }
        Page<GoodsAuditEntity> pageParam = new Page<>(searchModel.getPage().getPageNum(), searchModel.getPage().getPageSize());

        IPage<GoodsAuditEntity> queryResultPage = super.page(pageParam, lambdaQueryWrapper);
        List<GoodsAuditEntity> records = queryResultPage.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(e -> {
                GoodsDto goodsDto = JSON.parseObject(e.getDetailContent(), GoodsDto.class);
                List<SkuDto> skuList = goodsDto.getSkuList();
                GoodsManageAuditListVo vo = goodsAuditTransfer.assembleGoodsManageAuditListVo(e);
                vo.setSmallImage(goodsDto.getSmallImage());
                vo.setAuditStatus(AUDIT.name().equals(e.getStatus()) ? TO_AUDIT.name() : e.getStatus());
                vo.setGoodsTypeCode(goodsDto.getGoodsTypeCode());
                vo.setMinSellingPrice(skuList.stream().min(Comparator.comparing(SkuDto::getPrice)).get().getPrice());
                vo.setMaxSellingPrice(skuList.stream().max(Comparator.comparing(SkuDto::getPrice)).get().getPrice());
                vo.setSmallImage(goodsDto.getSmallImage());
                vo.setCreateTime(e.getCreateTime());
                CategoryEntity categoryEntity = categoryService.getById(e.getCategoryId());
                vo.setCategoryName(categoryEntity.getNamePath());
                vo.setShopName(genericService.getGenericShopInfoByShopId(e.getShopId(), e.getShopType(), true, false).getShopName());
                // fixme 一期暂时统一返回0
                vo.setTotalStock(0);
                vo.setSaleNumber(0);
                vo.setPurchaseNumber(0);
                resultList.add(vo);

            });
        }
        Page<GoodsManageAuditListVo> resultPage = new Page<>(searchModel.getPage().getPageNum(), searchModel.getPage().getPageSize());
        resultPage.setRecords(resultList);
        resultPage.setTotal(queryResultPage.getTotal());
        return PageUtil.pageToPageData(resultPage);
    }

    @Override
    public Boolean removeByIds(List<String> auditIds, String opUserId) {
        List<GoodsAuditEntity> goodsEntities = isExist(auditIds, null);
        goodsEntities.forEach(e -> {
            UpdateWrapper<GoodsAuditEntity> updateWrapper =
                    new UpdateWrapper<GoodsAuditEntity>()
                            .set("update_user", opUserId)
                            .set("update_time", new Date())
                            .set("deleted", TRUE.getValue())
                            .eq("id", e.getId());
            super.update(updateWrapper);
        });
        return true;
    }

    public Page<Map<String, Object>> auditPage(Page<?> pageParam, Map<String, Object> queryParam) {
        Page<Map<String, Object>> page = getBaseMapper().queryAuditPage(pageParam, queryParam);
        return page;
    }


    private List<GoodsAuditEntity> isExist(List<String> auditIds, String userId) {
        List<String> ids = auditIds.stream().distinct().collect(Collectors.toList());

        LambdaQueryWrapper<GoodsAuditEntity> lambdaQueryWrapper = new LambdaQueryWrapper<GoodsAuditEntity>()
                .eq(GoodsAuditEntity::getUserId, userId)
                .in(GoodsAuditEntity::getId, ids);
        List<GoodsAuditEntity> goodsEntities = super.list(lambdaQueryWrapper);
        if (ids.size() != goodsEntities.size()) {
            log.warn("商品id标识非法，param={}", JSON.toJSONString(ids));
            throw new BaseException(BaseCode.REQUIRED_REQUEST_BODY);
        }
        return goodsEntities;
    }
}
