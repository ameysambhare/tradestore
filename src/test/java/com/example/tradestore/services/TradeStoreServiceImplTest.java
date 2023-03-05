package com.example.tradestore.services;

import com.example.tradestore.exceptions.TradeExpiredException;
import com.example.tradestore.exceptions.VersionMismatchException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.model.TradeNumber;
import com.example.tradestore.repos.TradeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class TradeStoreServiceImplTest {
    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    TradeStoreServiceImpl service;

    @Test
    void addToStoreExpiredTest() throws TradeExpiredException, VersionMismatchException {
        Trade trade = new Trade();
        trade.setMaturityDate(LocalDate.of(2022, 6,10));
        assertThrows(TradeExpiredException.class, ()->service.addToStore(trade));
    }

    @Test
    void addToStoreVersionExceptionTest() throws TradeExpiredException, VersionMismatchException {
        Trade trade = new Trade();
        trade.setTradeId("1");
        trade.setVersion(2);
        trade.setMaturityDate(LocalDate.now());
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        Mockito.when(tradeRepository.findTradesByTradeId(anyString())).thenReturn(trades);
        Trade inputTrade = new Trade();
        inputTrade.setTradeId("1");
        inputTrade.setVersion(1);
        inputTrade.setMaturityDate(LocalDate.now());
        assertThrows(VersionMismatchException.class, ()->service.addToStore(inputTrade));
    }

    @Test
    void addToStoreSuccess() throws TradeExpiredException, VersionMismatchException {
        Trade trade = new Trade();
        trade.setTradeId("1");
        trade.setVersion(2);
        trade.setMaturityDate(LocalDate.now());
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        Mockito.when(tradeRepository.findTradesByTradeId(anyString())).thenReturn(trades);
        Trade inputTrade = new Trade();
        inputTrade.setTradeId("1");
        inputTrade.setVersion(3);
        inputTrade.setMaturityDate(LocalDate.now());
        Mockito.when(tradeRepository.save(inputTrade)).thenReturn(null);
        ResponseEntity<TradeNumber> responseEntity = service.addToStore(inputTrade);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getTradeId(), inputTrade.getTradeId());
        assertEquals(responseEntity.getBody().getVersion(), inputTrade.getVersion());
    }

    @Test
    void getAllTrades() {
        Trade trade = new Trade();
        trade.setTradeId("1");
        trade.setVersion(2);
        trade.setMaturityDate(LocalDate.now());
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        Mockito.when(tradeRepository.findAll()).thenReturn(trades);
        ResponseEntity<List<Trade>> response = service.getAllTrades();
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().size(), 1);
    }

    @Test
    void getTradesByTradeId() {
        Trade trade = new Trade();
        trade.setTradeId("1");
        trade.setVersion(2);
        trade.setMaturityDate(LocalDate.now());
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        Mockito.when(tradeRepository.findTradesByTradeId(anyString())).thenReturn(trades);
        ResponseEntity<List<Trade>> response = service.getTradesByTradeId("1");
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().size(), 1);
    }

    @Test
    void updateExpiryFlag() {
        Trade trade = new Trade();
        trade.setTradeId("1");
        trade.setVersion(2);
        trade.setMaturityDate(LocalDate.now());
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        Mockito.when(tradeRepository.findAll()).thenReturn(trades);
        service.updateExpiryFlag();
    }
}