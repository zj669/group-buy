package com.zj.groupbuy.controller.admin;


import com.zj.groupbuy.common.model.Response;
import com.zj.groupbuy.model.enums.ResponseCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.springframework.web.bind.annotation.*;


/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 */
@Slf4j
@RestController("/gbm/dcc/")
public class DCCController {

    @Resource
    private RTopic dccTopic;

    /**
     * 动态值变更
     * <p>
     * curl http://127.0.0.1:8091/api/v1/gbm/dcc/update_config?key=downgradeSwitch&value=1
     * curl http://127.0.0.1:8091/api/v1/gbm/dcc/update_config?key=cutRange&value=0
     */
    @GetMapping( "update_config")
    public Response<Boolean> updateConfig(@RequestParam String key, @RequestParam String value) {
        try {
            log.info("DCC 动态配置值变更 key:{} value:{}", key, value);
            dccTopic.publish(key + "," + value);
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("DCC 动态配置值变更失败 key:{} value:{}", key, value, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
