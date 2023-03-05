package com.example.tradestore.controllers;

import com.example.tradestore.constants.ApplicationConstants;
import com.example.tradestore.exceptions.TradeExpiredException;
import com.example.tradestore.exceptions.VersionMismatchException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.model.TradeNumber;
import com.example.tradestore.services.TradeStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tradestore")
@Slf4j
public class TradeController {

    @Autowired
    TradeStoreService tradeStoreService;

    @PostMapping("/addTrade")
    public TradeNumber addTrade(@Valid @RequestBody Trade trade) throws TradeExpiredException, VersionMismatchException {
        log.info(ApplicationConstants.INSIDE_ADD_TRADE_CONTROLLER_);
        ResponseEntity<TradeNumber> response = tradeStoreService.addToStore(trade);
        if (response != null) {
            return response.getBody();
        }
        return null;
    }

    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        log.info(ApplicationConstants.INSIDE_GET_ALL_TRADES_CONTROLLER_);
        ResponseEntity<List<Trade>> responseEntity = tradeStoreService.getAllTrades();
        if (responseEntity != null) {
            return responseEntity.getBody();
        }
        return null;
    }

    @GetMapping("/trades/{tradeId}")
    public List<Trade> getTradesByTradeId(@PathVariable("tradeId") String tradeId) {
        log.info(ApplicationConstants.INSIDE_GET_TRADES_BY_TRADE_ID_CONTROLLER);
        ResponseEntity<List<Trade>> responseEntity = tradeStoreService.getTradesByTradeId(tradeId);
        if (responseEntity != null) {
            return responseEntity.getBody();
        }
        return null;
    }
}
