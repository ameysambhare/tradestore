package com.example.tradestore.services;

import com.example.tradestore.constants.ApplicationConstants;
import com.example.tradestore.exceptions.TradeExpiredException;
import com.example.tradestore.exceptions.VersionMismatchException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.model.TradeNumber;
import com.example.tradestore.repos.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TradeStoreServiceImpl implements TradeStoreService {

    @Autowired
    TradeRepository tradeRepository;
    @Override
    public ResponseEntity<TradeNumber> addToStore(Trade trade) throws TradeExpiredException, VersionMismatchException {
        log.info(ApplicationConstants.INSIDE_ADD_TO_STORE_METHOD);
        if(validTrade(trade)) {
            tradeRepository.save(trade);
            log.info(ApplicationConstants.AFTER_INSERTING_INTO_TABLE);
            TradeNumber tradeNumber =  new TradeNumber();
            tradeNumber.setTradeId(trade.getTradeId());
            tradeNumber.setVersion(trade.getVersion());
            return new ResponseEntity<>(tradeNumber, HttpStatus.ACCEPTED);
        }
        return null;

    }
     public ResponseEntity <List<Trade>> getAllTrades() {
        List<Trade> trades = (List<Trade>) tradeRepository.findAll();
         return new ResponseEntity<>(trades, HttpStatus.OK);
     }

     public ResponseEntity<List<Trade>> getTradesByTradeId(String tradeId) {
         List<Trade> trades = tradeRepository.findTradesByTradeId(tradeId);
         return new ResponseEntity<>(trades, HttpStatus.OK);
     }

    @Override
    public void updateExpiryFlag() {
        ResponseEntity<List<Trade>> trades = getAllTrades();
        List<Trade> tradeList = trades.getBody();
        assert tradeList != null;
        List<Trade> updateTradeList = tradeList.stream().filter(trade -> trade.getMaturityDate().isBefore(LocalDate.now())).collect(Collectors.toList());
        updateTradeList.forEach(trade-> trade.setIsExpired("Y"));
        tradeRepository.saveAll(updateTradeList);

    }

    private boolean validTrade(Trade trade) throws TradeExpiredException, VersionMismatchException {
        if(isTradeExpired(trade)) {
            throw new TradeExpiredException(ApplicationConstants.TRADEEXPIREDEXCEPTIONMESSAGE+trade.getMaturityDate());
        }
        if(oldVersionTrade(trade)) {
            throw new VersionMismatchException(ApplicationConstants.VERSIONMISMATCHEXCEPTIONMESSAGE+trade.getVersion());
         }
        return true;
    }

    private boolean oldVersionTrade(Trade trade) {
        List<Trade> trades = tradeRepository.findTradesByTradeId(trade.getTradeId());
        return trades.stream().anyMatch(tr-> tr.getVersion()>trade.getVersion());
    }

    private boolean isTradeExpired(Trade trade) {
        return (trade.getMaturityDate().isBefore(LocalDate.now()));
    }


}
