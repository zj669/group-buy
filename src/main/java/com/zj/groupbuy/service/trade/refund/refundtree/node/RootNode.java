package com.zj.groupbuy.service.trade.refund.refundtree.node;

import com.zj.groupbuy.model.entity.GroupBuyOrder;
import com.zj.groupbuy.model.entity.GroupBuyOrderList;
import com.zj.groupbuy.model.enums.GroupBuyOrderEnum;
import com.zj.groupbuy.model.enums.TradeOrderStatusEnum;
import com.zj.groupbuy.service.trade.refund.refundtree.base.IBaseHandler;
import com.zj.groupbuy.service.trade.refund.refundtree.facotory.RefundFactory;
import com.zj.groupbuy.service.trade.refund.reponsitory.ITradeRefundRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component("refundRootNode")
public class RootNode extends AbsRefund{
    @Resource
    private ITradeRefundRepository repository;
    @Resource
    private DefaulteRefundNode defaulteRefundNode;
    @Resource
    private OrderInitNode orderInitNode;
    @Resource
    private OrderCompileNode orderCompileNode;
    @Resource
    private TeamProgressNode teamProgressNode;
    @Override
    protected IBaseHandler<RefundFactory.RefundRequestEntity, RefundFactory.RefundResponseEntity, RefundFactory.RefundContextEntity> route(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {
        GroupBuyOrder team = refundContextEntity.getTeam();
        GroupBuyOrderList order = refundContextEntity.getOrder();
        if(Objects.isNull( team) || Objects.isNull( order)){
            return defaulteRefundNode;
        }
        if(GroupBuyOrderEnum.COMPLETE.equals(team.getStatus())){
            // 走拼团完成
            if(TradeOrderStatusEnum.CREATE.equals(order.getStatus())){
                return orderInitNode;
            }
            if(TradeOrderStatusEnum.COMPLETE.equals(order.getStatus())){
                return orderCompileNode;
            }
        }
        if(GroupBuyOrderEnum.PROGRESS.equals(team.getStatus())){
            return teamProgressNode;
        }
        // 其余状况不需要做处理
        return defaulteRefundNode;
    }

    @Override
    protected void init(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {
        // 查拼团信息

        //  查订单信息
    }

    @Override
    public RefundFactory.RefundResponseEntity doHandle(RefundFactory.RefundRequestEntity refundRequestEntity, RefundFactory.RefundContextEntity refundContextEntity) {

        return router(refundRequestEntity,refundContextEntity);
    }
}
