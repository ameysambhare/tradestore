package com.example.tradestore.repos;

import com.example.tradestore.model.Trade;
import com.example.tradestore.model.TradeNumber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends CrudRepository<Trade, TradeNumber> {

    @Query("select t from Trade t where t.tradeId = :tradeId")
    List<Trade> findTradesByTradeId(@Param("tradeId") String tradeId);
}
