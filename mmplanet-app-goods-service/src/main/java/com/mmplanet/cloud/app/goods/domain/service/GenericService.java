package com.mmplanet.cloud.app.goods.domain.service;

import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsDetailRespDto;
import com.mmplanet.cloud.app.goods.domain.service.impl.GenericServiceImpl;
import com.mmplanet.cloud.app.user.vo.AppUserVo;

/**
 * 通用Service
 *
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 11:15 <br>
 * @Author: niujiao
 */
public interface GenericService {

    /**
     * 根据用户ID和用户类型获取店铺、工厂信息
     *
     * @param userId 用户ID
     * @param userType 用户类型
     * @param ignoreStatus 是否忽略状态
     * @return
     */
    GenericServiceImpl.GenericShopInfoDto getGenericShopInfoByUserId(String userId, String userType, boolean ignoreStatus, boolean throwEx);

    /**
     * 根据店铺ID和类型获取店铺、工厂信息
     * @param shopId
     * @param userType
     * @param ignoreStatus
     * @param throwEx
     * @return
     */
    GenericServiceImpl.GenericShopInfoDto getGenericShopInfoByShopId(String shopId, String userType, boolean ignoreStatus, boolean throwEx);
    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    AppUserVo getUser(String userId);


    GoodsDetailRespDto.ShopRespDto assembleGoodsShopVo(GenericServiceImpl.GenericShopInfoDto shopInfoDto);

}
