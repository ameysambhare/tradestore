package com.example.tradestore.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TradeNumber implements Serializable {
    @Column(name = "TradeId")
    @NotBlank
    private String tradeId;
    @Column(name = "Version")
    @NotNull
    private Integer version;

    public TradeNumber() {
    }

    public TradeNumber(String tradeId, Integer version) {
        this.tradeId = tradeId;
        this.version = version;
    }
}
