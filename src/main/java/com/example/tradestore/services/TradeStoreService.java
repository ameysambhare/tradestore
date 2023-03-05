package com.example.tradestore.services;

import com.example.tradestore.exceptions.TradeExpiredException;
import com.example.tradestore.exceptions.VersionMismatchException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.model.TradeNumber;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TradeStoreService {
    ResponseEntity<TradeNumber> addToStore(Trade trade) throws TradeExpiredException, VersionMismatchException;
    ResponseEntity <List<Trade>> getAllTrades();
    ResponseEntity<List<Trade>> getTradesByTradeId(String tradeId);
    void updateExpiryFlag();
}
