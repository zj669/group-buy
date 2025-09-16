package com.zj.groupbuy.common.design.ruletree.factory;



import com.zj.groupbuy.common.design.ruletree.AbstractMultiThreadStrategyRouter;
import lombok.Data;


@Data
public abstract class DefaultMultiThreadStrategyFactory<Request, Context, Result> {

    public abstract AbstractMultiThreadStrategyRouter<Request, Context, Result> getRootNode();

}
