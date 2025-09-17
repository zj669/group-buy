package com.zj.groupbuy.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.groupbuy.model.entity.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface GroupBuyOrderListMapper extends BaseMapper<GroupBuyOrderList> {
    int updateOrderStatus2COMPLETE(GroupBuyOrderList groupBuyOrderListReq);
}