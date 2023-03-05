package com.example.tradestore.controllers;

import com.example.tradestore.exceptions.TradeExpiredException;
import com.example.tradestore.exceptions.VersionMismatchException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.model.TradeNumber;
import com.example.tradestore.services.TradeStoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TradeControllerTest {
    @Mock
    TradeStoreService service;

    @InjectMocks
    TradeController tradeController;

    @Test
    void addTrade() throws TradeExpiredException, VersionMismatchException {
        TradeNumber tradeNumber = new TradeNumber();
        tradeNumber.setTradeId("1");
        tradeNumber.setVersion(1);
        ResponseEntity<TradeNumber> tradeResponse = new ResponseEntity<>(tradeNumber, HttpStatus.OK);
        Mockito.when(service.addToStore(Mockito.any())).thenReturn(tradeResponse);
        Trade trade = new Trade();
        TradeNumber responseTradeNumber = tradeController.addTrade(trade);
        assertNotNull(responseTradeNumber);
        assertEquals(responseTradeNumber.getTradeId(), tradeNumber.getTradeId());
        assertEquals(responseTradeNumber.getVersion(), tradeNumber.getVersion());
    }

    @Test
    void addTradeNullResponse() throws TradeExpiredException, VersionMismatchException {
        Mockito.when(service.addToStore(Mockito.any())).thenReturn(null);
        Trade trade = new Trade();
        TradeNumber responseTradeNumber = tradeController.addTrade(trade);
        assertNull(responseTradeNumber);
    }

    @Test
    void getAllTrades() {
        Trade trade = new Trade();
        trade.setTradeId("1");
        trade.setVersion(1);
        trade.setBookId("book-id");
        trade.setCounterPartyId("counter-party-id");
        trade.setMaturityDate(LocalDate.of(2024,6,19));
        trade.setCreatedDate(LocalDate.now());
        trade.setIsExpired("N");
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        ResponseEntity<List<Trade>> responseEntity =  new ResponseEntity<>(trades, HttpStatus.OK);
        Mockito.when(service.getAllTrades()).thenReturn(responseEntity);
        List<Trade> responseTrades = tradeController.getAllTrades();
        assertEquals(1, responseTrades.size());
        assertEquals(responseTrades.get(0).getTradeId(), "1");
        assertEquals(responseTrades.get(0).getCounterPartyId(), "counter-party-id");
    }

    @Test
    void getAllTradesNullReponse() {
        Mockito.when(service.getAllTrades()).thenReturn(null);
        List<Trade> responseTrades = tradeController.getAllTrades();
        assertNull(responseTrades);
    }

    @Test
    void getTradesByTradeId() {
        Trade trade = new Trade();
        trade.setTradeId("1");
        trade.setVersion(1);
        trade.setBookId("book-id");
        trade.setCounterPartyId("counter-party-id");
        trade.setMaturityDate(LocalDate.of(2024,6,19));
        trade.setCreatedDate(LocalDate.now());
        trade.setIsExpired("N");
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        ResponseEntity<List<Trade>> responseEntity =  new ResponseEntity<>(trades, HttpStatus.OK);
        Mockito.when(service.getTradesByTradeId(Mockito.anyString())).thenReturn(responseEntity);
        List<Trade> responseTrades = tradeController.getTradesByTradeId("1");
        assertEquals(1, responseTrades.size());
        assertEquals(responseTrades.get(0).getTradeId(), "1");
        assertEquals(responseTrades.get(0).getBookId(), "book-id");
    }
}