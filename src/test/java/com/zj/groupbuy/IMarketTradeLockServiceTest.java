package com.zj.groupbuy;

import com.alibaba.fastjson2.JSON;
import com.zj.groupbuy.common.model.Response;
import com.zj.groupbuy.controller.client.MarketTradeController;
import com.zj.groupbuy.model.dto.LockMarketPayOrderRequestDTO;
import com.zj.groupbuy.model.dto.LockMarketPayOrderResponseDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@SpringBootTest
public class IMarketTradeLockServiceTest {
    @Resource
    private MarketTradeController marketTradeController;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Test
    public void test_lockMarketPayOrder() {


//        threadPoolExecutor.execute(() -> {
//            testlock("zj02");
//        });
//        threadPoolExecutor.execute(() -> {
//            testlock("zj03");
//        });
        threadPoolExecutor.execute(() -> {
            testlock("zj04");
        });
    }

    private void testlock(String name){
        LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO = new LockMarketPayOrderRequestDTO();
        lockMarketPayOrderRequestDTO.setUserId(name);
        lockMarketPayOrderRequestDTO.setTeamId("81597078");
        lockMarketPayOrderRequestDTO.setActivityId(100123L);
        lockMarketPayOrderRequestDTO.setGoodsId("9890001");
        lockMarketPayOrderRequestDTO.setSource("s01");
        lockMarketPayOrderRequestDTO.setChannel("c01");
        lockMarketPayOrderRequestDTO.setOutTradeNo(RandomStringUtils.randomNumeric(12));
        Response<LockMarketPayOrderResponseDTO> lockMarketPayOrderResponseDTOResponse = marketTradeController.lockMarketPayOrder(lockMarketPayOrderRequestDTO);
        log.info("测试结果 req:{} res:{}", JSON.toJSONString(lockMarketPayOrderRequestDTO), JSON.toJSONString(lockMarketPayOrderResponseDTOResponse));
    }


    @Test
    public void test_lockMarketPayOrder_teamId_not_null() {
        LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO = new LockMarketPayOrderRequestDTO();
        lockMarketPayOrderRequestDTO.setUserId("zj01");
        lockMarketPayOrderRequestDTO.setTeamId(null);
        lockMarketPayOrderRequestDTO.setActivityId(100123L);
        lockMarketPayOrderRequestDTO.setGoodsId("9890001");
        lockMarketPayOrderRequestDTO.setSource("s01");
        lockMarketPayOrderRequestDTO.setChannel("c01");
        lockMarketPayOrderRequestDTO.setOutTradeNo(RandomStringUtils.randomNumeric(12));
        Response<LockMarketPayOrderResponseDTO> lockMarketPayOrderResponseDTOResponse = marketTradeController.lockMarketPayOrder(lockMarketPayOrderRequestDTO);
        log.info("测试结果 req:{} res:{}", JSON.toJSONString(lockMarketPayOrderRequestDTO), JSON.toJSONString(lockMarketPayOrderResponseDTOResponse));
    }

}
