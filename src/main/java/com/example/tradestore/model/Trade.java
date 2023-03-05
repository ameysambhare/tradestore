package com.example.tradestore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "TradeStore")
@IdClass(TradeNumber.class)
@Data
public class Trade {
    @Id
    @NotBlank
    private String tradeId;
    @Id
    @NotNull
    private Integer version;
    @Column(name = "CounterPartyId")
    @NotBlank
    private String counterPartyId;
    @Column(name = "BookId")
    @NotBlank
    private String bookId;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "MaturityDate")
    private LocalDate maturityDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "CreatedDate")
    private LocalDate createdDate;
    @Column(name = "Expired")
    private String isExpired = "N";
}
