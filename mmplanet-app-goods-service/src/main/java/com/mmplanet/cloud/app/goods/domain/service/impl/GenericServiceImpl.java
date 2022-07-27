package com.mmplanet.cloud.app.goods.domain.service.impl;

import com.mmplanet.cloud.app.common.code.BaseCode;
import com.mmplanet.cloud.app.common.exception.BaseException;
import com.mmplanet.cloud.app.goods.application.dto.resp.GoodsDetailRespDto;
import com.mmplanet.cloud.app.goods.domain.service.GenericService;
import com.mmplanet.cloud.app.goods.infra.integration.DealerApiClient;
import com.mmplanet.cloud.app.goods.infra.integration.ShopApiClient;
import com.mmplanet.cloud.app.goods.infra.integration.UserApiClient;
import com.mmplanet.cloud.app.user.enums.UserTypeEnum;
import com.mmplanet.cloud.app.user.vo.AppUserVo;
import com.mmplanet.cloud.app.user.vo.DealerVo;
import com.mmplanet.cloud.app.user.vo.ShopVo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/16 11:18 <br>
 * @Author: niujiao
 */
@Service
public class GenericServiceImpl implements GenericService {

    public static final String ACTIVE = "active";
    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private ShopApiClient shopApiClient;

    @Autowired
    private DealerApiClient dealerApiClient;

    /**
     * 通过用户标识获取信息
     * @param userId
     * @param userType
     * @param ignoreStatus
     * @param throwEx
     * @return
     */
    public GenericShopInfoDto getGenericShopInfoByUserId(String userId, String userType, boolean ignoreStatus, boolean throwEx) {

        GenericShopInfoDto genericShopInfoDto = getGenericShopInfoByUserId(userId, userType);

        if (!ignoreStatus && genericShopInfoDto != null && !ACTIVE.equals(genericShopInfoDto.getStatus())) {
            genericShopInfoDto = null;
        }

        if (throwEx && genericShopInfoDto == null) {
            throw new BaseException(BaseCode.FAIL, "店铺未开通或已关闭！");
        }
        return genericShopInfoDto;
    }

    /**
     * 通过店铺标识获取信息
     * @param shopId
     * @param shopType
     * @param ignoreStatus
     * @param throwEx
     * @return
     */
    public GenericShopInfoDto getGenericShopInfoByShopId(String shopId, String shopType, boolean ignoreStatus, boolean throwEx) {

        GenericShopInfoDto genericShopInfoDto = getGenericShopInfoByShopId(shopId, shopType);

        if (!ignoreStatus && genericShopInfoDto != null && !ACTIVE.equals(genericShopInfoDto.getStatus())) {
            genericShopInfoDto = null;
        }

        if (throwEx && genericShopInfoDto == null) {
            throw new BaseException(BaseCode.FAIL, "店铺未开通！");
        }
        return genericShopInfoDto == null ? new GenericShopInfoDto() : genericShopInfoDto;
    }


    private GenericShopInfoDto getGenericShopInfoByShopId(String shopId, String shopType) {
        GenericShopInfoDto genericShopInfoDto = null;
        if (UserTypeEnum.SMALL_B.name().equalsIgnoreCase(shopType)) {
            ShopVo shop = shopApiClient.getShop(shopId);
            if (shop != null) {
                genericShopInfoDto = new GenericShopInfoDto();
                genericShopInfoDto.setShopId(shop.getId());
                genericShopInfoDto.setShopName(shop.getShopName());
                genericShopInfoDto.setShopType(UserTypeEnum.SMALL_B.name());
                genericShopInfoDto.setStatus(shop.getStatus());
                genericShopInfoDto.setShopIcon(shop.getShopIcon());
                genericShopInfoDto.setReturnRate(shop.getReturnRate());
                genericShopInfoDto.setShopLevel(shop.getShopLevel());
                genericShopInfoDto.setFans(shop.getShopLevel());
                genericShopInfoDto.setLastLoginTime(shop.getLastLoginTime());
                genericShopInfoDto.setLastLoginTimeDesc(shop.getLastLoginTimeDesc());
            }
        } else if (UserTypeEnum.DEALER.name().equalsIgnoreCase(shopType)) {
            DealerVo dealer = dealerApiClient.getDealer(shopId);
            if (dealer != null) {
                genericShopInfoDto = new GenericShopInfoDto();
                genericShopInfoDto.setShopId(dealer.getId());
                genericShopInfoDto.setShopName(dealer.getDealerName());
                genericShopInfoDto.setShopType(UserTypeEnum.DEALER.name());
                genericShopInfoDto.setStatus(dealer.getStatus());
                genericShopInfoDto.setShopIcon(dealer.getDealerIcon());
                genericShopInfoDto.setReturnRate(dealer.getReturnRate());
                genericShopInfoDto.setShopLevel(dealer.getShopLevel());
                genericShopInfoDto.setFans(dealer.getShopLevel());
                genericShopInfoDto.setLastLoginTime(dealer.getLastLoginTime());
                genericShopInfoDto.setLastLoginTimeDesc(dealer.getLastLoginTimeDesc());
            }
        }
        return genericShopInfoDto;
    }

    private GenericShopInfoDto getGenericShopInfoByUserId(String userId, String userType) {
        GenericShopInfoDto genericShopInfoDto = null;
        if (UserTypeEnum.SMALL_B.name().equalsIgnoreCase(userType)) {
            ShopVo shop = shopApiClient.getShopByUserId(userId);
            if (shop != null) {
                genericShopInfoDto = new GenericShopInfoDto();
                genericShopInfoDto.setUserId(shop.getUserId());
                genericShopInfoDto.setShopId(shop.getId());
                genericShopInfoDto.setShopName(shop.getShopName());
                genericShopInfoDto.setShopType(UserTypeEnum.SMALL_B.name());
                genericShopInfoDto.setStatus(shop.getStatus());
                genericShopInfoDto.setShopIcon(shop.getShopIcon());
                genericShopInfoDto.setShopLevel(shop.getShopLevel());
                genericShopInfoDto.setFans(shop.getFans());
                genericShopInfoDto.setReturnRate(shop.getReturnRate());
            }
        } else if (UserTypeEnum.DEALER.name().equalsIgnoreCase(userType)) {
            DealerVo dealer = dealerApiClient.getDealerByUserId(userId);
            if (dealer != null) {
                genericShopInfoDto = new GenericShopInfoDto();
                genericShopInfoDto.setUserId(dealer.getUserId());
                genericShopInfoDto.setShopId(dealer.getId());
                genericShopInfoDto.setShopName(dealer.getDealerName());
                genericShopInfoDto.setShopType(UserTypeEnum.DEALER.name());
                genericShopInfoDto.setStatus(dealer.getStatus());
                genericShopInfoDto.setShopIcon(dealer.getDealerIcon());
                genericShopInfoDto.setShopLevel(dealer.getShopLevel());
                genericShopInfoDto.setFans(dealer.getFans());
                genericShopInfoDto.setReturnRate(dealer.getReturnRate());
            }
        }
        return genericShopInfoDto;
    }

    public GoodsDetailRespDto.ShopRespDto assembleGoodsShopVo(GenericServiceImpl.GenericShopInfoDto shopInfoDto) {
        GoodsDetailRespDto.ShopRespDto dto = new GoodsDetailRespDto.ShopRespDto();
        dto.setShopType(shopInfoDto.getShopType());
        dto.setShopName(shopInfoDto.getShopName());
        dto.setShopIcon(shopInfoDto.getShopIcon());
        dto.setShopLevel(shopInfoDto.getShopLevel());
        dto.setReturnRate(shopInfoDto.getReturnRate());
        dto.setFans(shopInfoDto.getFans());
        dto.setLastLoginTime(shopInfoDto.getLastLoginTime());
        dto.setLastLoginTimeDesc(shopInfoDto.getLastLoginTimeDesc());
        return dto;
    }

    @Data
    public static class GenericShopInfoDto {
        private String userId;
        private String shopId;
        private String shopType;
        private String shopName;
        private String status;
        /**
         * 店铺icon
         **/
        private String shopIcon;

        /**
         * 店铺等级
         **/
        private Integer shopLevel;
        /**
         * 回头率
         **/
        private BigDecimal returnRate;
        /**
         * 粉丝数
         **/
        private Integer fans;

        private Date lastLoginTime;

        private String lastLoginTimeDesc;
    }

    @Override
    public AppUserVo getUser(String userId) {
        return userApiClient.getUser(userId);
    }
}
