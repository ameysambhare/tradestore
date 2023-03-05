package com.example.tradestore.exceptions;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ErrorMessage {
    private String error;

    public ErrorMessage(String error, LocalDate date) {
        this.error = error;
        this.date = date;
    }

    private LocalDate date;
}
