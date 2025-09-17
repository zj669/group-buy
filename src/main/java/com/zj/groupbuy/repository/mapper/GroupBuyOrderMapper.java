package com.zj.groupbuy.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.groupbuy.model.entity.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface GroupBuyOrderMapper extends BaseMapper<GroupBuyOrder> {
    int updateAddLockCount(String teamId);

    int updateAddCompleteCount(String teamId);

    int updateOrderStatus2COMPLETE(String teamId);
}