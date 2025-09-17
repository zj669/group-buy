package com.zj.groupbuy.repository.activity;

import com.zj.groupbuy.model.entity.CrowdTagsDetail;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.entity.GroupBuyDiscount;
import com.zj.groupbuy.model.entity.Sku;

public interface IActivityRepository {
    GroupBuyActivity selectActivityById(Long activityId);

    Sku selectSkuByIdAndSC(String goodsId, String channel, String source);

    boolean downgradeSwitch();

    boolean cutRange(String userId);

    CrowdTagsDetail selectCrowdTagsDetailByUserId(String userId);

    GroupBuyDiscount selectDiscountById(String discountId);

    GroupBuyActivity selectActivityByGoodsId(String goodsId, String channel, String source);
}
