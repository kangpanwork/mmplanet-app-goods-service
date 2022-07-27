package com.mmplanet.cloud.app.goods.application;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.common.page.PageData;
import com.mmplanet.cloud.app.common.request.SearchModel;
import com.mmplanet.cloud.app.goods.domain.service.AttributeService;
import com.mmplanet.cloud.app.goods.domain.transfer.AttributeTransfer;
import com.mmplanet.cloud.app.goods.dto.AttributeDto;
import com.mmplanet.cloud.app.goods.dto.AttributePageQueryDto;
import com.mmplanet.cloud.app.goods.dto.DeleteAttributeDto;
import com.mmplanet.cloud.app.goods.infra.entity.AttributeEntity;
import com.mmplanet.cloud.app.goods.infra.util.PageDataUtils;
import com.mmplanet.cloud.app.goods.vo.OpsAttributeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * 商品属性表 应用层
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/11 15:26 <br>
 * @Author: jacky
 */
@Component
public class AttributeApplication {

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AttributeTransfer attributeTransfer;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    /**
     * 查询分页数据
     */
    public PageData<OpsAttributeVo> pageQuery(SearchModel<AttributePageQueryDto> searchModel) {

        Page<AttributeEntity> pageParam = new Page<>(searchModel.getPage().getPageNum(), searchModel.getPage().getPageSize());
        LambdaQueryWrapper<AttributeEntity> attrQuery = Wrappers.<AttributeEntity>lambdaQuery().orderByDesc(AttributeEntity::getCreateTime);

        IPage<AttributeEntity> resultPage = attributeService.page(pageParam, attrQuery);

        List<OpsAttributeVo> data = attributeTransfer.assembleOpsAttributeVo(resultPage.getRecords());

        return PageDataUtils.<OpsAttributeVo>build(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal(), resultPage.getPages(), data);
    }


    public OpsAttributeVo detail(String id) {
        AttributeEntity attributeEntity = attributeService.getById(id);
        return attributeTransfer.assembleOpsAttributeVo(attributeEntity);
    }

    /**
     * 新增或者修改
     */
    @Transactional(rollbackFor = Exception.class)
    public String save(AttributeDto dto) {
        Lock lock = redisLockRegistry.obtain("APP:GOODS:ATTRIBUTE:" + dto.getName());
        if (lock.tryLock()) {
            try {
                AttributeEntity entity = attributeTransfer.toModel(dto);
                if (StringUtils.isEmpty(entity.getId())) {
                    attributeService.insert(entity);
                } else {
                    attributeService.modify(entity);
                }
                return entity.getId();
            } finally {
                lock.unlock();
            }
        } else {
            throw new BaseException(BaseCode.NO_LOCK_ACQUIRED);
        }
    }

    public Boolean delete(DeleteAttributeDto dto) {
        AttributeEntity attributeEntity = new AttributeEntity();
        attributeEntity.setId(dto.getId());
        attributeEntity.setUpdateUser(dto.getOpUserId());
        return attributeService.removeById(attributeEntity);
    }
}
