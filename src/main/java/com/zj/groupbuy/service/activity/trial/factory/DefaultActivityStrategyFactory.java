package com.zj.groupbuy.service.activity.trial.factory;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DefaultActivityStrategyFactory {


    @Data
    public static class DynamicContext{

        /**
         * 是否可见
         */
        private Boolean isVisible;

        /**
         * 是否可参与
         */
        private Boolean isEnable;

    }
}
