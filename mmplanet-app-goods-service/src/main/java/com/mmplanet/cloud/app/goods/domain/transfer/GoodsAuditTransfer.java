package com.mmplanet.cloud.app.goods.domain.transfer;

import com.mmplanet.cloud.app.goods.dto.GoodsDto;
import com.mmplanet.cloud.app.goods.infra.entity.GoodsAuditEntity;
import com.mmplanet.cloud.app.goods.vo.GoodsManageAuditListVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/5/26 18:24 <br>
 * @Author: niujiao
 */
@Mapper(componentModel = "spring")
public interface GoodsAuditTransfer {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "goodsId", source="id")
    GoodsAuditEntity convert(GoodsDto dto);

    GoodsManageAuditListVo assembleGoodsManageAuditListVo(GoodsAuditEntity e);
}
