package com.zj.groupbuy.repository.activity.impl;

import com.zj.groupbuy.integretion.dcc.DCCService;
import com.zj.groupbuy.model.entity.CrowdTagsDetail;
import com.zj.groupbuy.model.entity.GroupBuyActivity;
import com.zj.groupbuy.model.entity.GroupBuyDiscount;
import com.zj.groupbuy.model.entity.ScSkuActivity;
import com.zj.groupbuy.model.entity.Sku;
import com.zj.groupbuy.repository.activity.IActivityRepository;
import com.zj.groupbuy.repository.mapper.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Component
public class ActivityRepository implements IActivityRepository {
    @Resource
    private GroupBuyActivityMapper groupBuyActivityMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private CrowdTagsDetailMapper crowdTagsDetailMapper;

    @Resource
    private GroupBuyDiscountMapper groupBuyDiscountMapper;
    @Resource
    private ScSkuActivityMapper scSkuActivityMapper;

    @Resource
    private DCCService dccService;

    @Override
    public GroupBuyActivity selectActivityById(Long activityId) {
        QueryWrapper<GroupBuyActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id", activityId);
        return groupBuyActivityMapper.selectOne(wrapper);
    }

    @Override
    public Sku selectSkuByIdAndSC(String goodsId, String channel, String source) {
        QueryWrapper<Sku> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId)
               .eq("channel", channel)
               .eq("source", source);
        return skuMapper.selectOne(wrapper);
    }

    @Override
    public boolean downgradeSwitch() {
        // 这个方法的实现需要根据具体业务逻辑来确定
        // 目前返回默认值false
        return dccService.isDowngradeSwitch();
    }

    @Override
    public boolean cutRange(String userId) {
        // 这个方法的实现需要根据具体业务逻辑来确定
        // 目前返回默认值false
        return dccService.isCutRange(userId);
    }

    @Override
    public CrowdTagsDetail selectCrowdTagsDetailByUserId(String userId) {
        QueryWrapper<CrowdTagsDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return crowdTagsDetailMapper.selectOne(wrapper);
    }

    @Override
    public GroupBuyDiscount selectDiscountById(String discountId) {
        QueryWrapper<GroupBuyDiscount> wrapper = new QueryWrapper<>();
        wrapper.eq("discount_id", discountId);
        return groupBuyDiscountMapper.selectOne(wrapper);
    }

    @Override
    public GroupBuyActivity selectActivityByGoodsId(String goodsId, String channel, String source) {
        QueryWrapper<ScSkuActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId)
                .eq("channel", channel)
                .eq("source", source);
        ScSkuActivity sku = scSkuActivityMapper.selectOne(wrapper);
        if (sku == null) {
            return null;
        }
        QueryWrapper<GroupBuyActivity> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("activity_id", sku.getActivityId());
        return groupBuyActivityMapper.selectOne(wrapper1);
    }
}
