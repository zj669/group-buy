package com.zj.groupbuy.service.trade.refund.reponsitory.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.groupbuy.model.entity.GroupBuyOrderList;
import com.zj.groupbuy.model.enums.TradeOrderStatusEnum;
import com.zj.groupbuy.repository.mapper.GroupBuyOrderListMapper;
import com.zj.groupbuy.repository.mapper.GroupBuyOrderMapper;
import com.zj.groupbuy.service.trade.refund.reponsitory.ITradeRefundRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class TradeRefundRepository implements ITradeRefundRepository {
    @Resource
    private GroupBuyOrderListMapper groupBuyOrderListMapper;
    @Override
    public List<GroupBuyOrderList> selectByOutTimeAndNotPay() {
        List<GroupBuyOrderList> groupBuyOrderLists = groupBuyOrderListMapper.selectList(new LambdaQueryWrapper<GroupBuyOrderList>()
                .eq(GroupBuyOrderList::getStatus, TradeOrderStatusEnum.CREATE)
        );
        if(CollectionUtils.isEmpty(groupBuyOrderLists)){
            return List.of();
        }
        return groupBuyOrderLists;
    }
}
