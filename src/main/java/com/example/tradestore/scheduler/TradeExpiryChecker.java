package com.example.tradestore.scheduler;

import com.example.tradestore.constants.ApplicationConstants;
import com.example.tradestore.services.TradeStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TradeExpiryChecker {
    @Autowired
    TradeStoreService tradeStoreService;

    /*
    Changes to call the method once everyday
     */
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void updateExpiredTrades() {
        log.info(ApplicationConstants.INSIDE_UPDATE_EXPIRED_TRADES_METHOD_SCHEDULER, LocalDate.now());
        tradeStoreService.updateExpiryFlag();
    }

}
