package com.mmplanet.cloud.app.goods.controller.convert;

import com.mmplanet.cloud.app.goods.api.ops.dto.OpsGoodsListQueryDto;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsManagePageQuery;
import com.mmplanet.cloud.app.goods.application.dto.query.GoodsPageQuery;
import com.mmplanet.cloud.app.goods.dto.GoodsManagePageQueryDto;
import com.mmplanet.cloud.app.goods.dto.GoodsPageQueryDto;
import com.mmplanet.cloud.app.goods.dto.ShopHomePageQueryDto;
import org.mapstruct.Mapper;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/7/23 17:32 <br>
 * @Author: niujiao
 */
@Mapper(componentModel = "spring")
public interface GoodsConvert {

    GoodsPageQuery convert(GoodsPageQueryDto dto);

    GoodsManagePageQuery convert(GoodsManagePageQueryDto dto);

    GoodsManagePageQuery convert(OpsGoodsListQueryDto model);

    GoodsPageQuery convert(ShopHomePageQueryDto model);
}
