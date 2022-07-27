package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmplanet.cloud.app.goods.domain.service.AuditProcessService;
import com.mmplanet.cloud.app.goods.infra.entity.AuditProcessEntity;
import com.mmplanet.cloud.app.goods.infra.mapper.AuditProcessMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审核过程表 服务实现类
 * </p>
 *
 * @author niujiao
 * @since 2022-07-05
 */
@Service
public class AuditProcessServiceImpl extends ServiceImpl<AuditProcessMapper, AuditProcessEntity> implements AuditProcessService {

}
