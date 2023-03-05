package com.example.tradestore.controlleradvice;

import com.example.tradestore.exceptions.ErrorMessage;
import com.example.tradestore.exceptions.TradeExpiredException;
import com.example.tradestore.exceptions.VersionMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class TradestoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TradeExpiredException.class})
    public ResponseEntity<Object> handleTradeExpiredException (Exception ex, WebRequest request) {
        ErrorMessage errorMessage =  new ErrorMessage(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value= {VersionMismatchException.class})
    public ResponseEntity<Object> handleVersionMismatchException (Exception ex, WebRequest request) {
        ErrorMessage errorMessage =  new ErrorMessage(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException (Exception ex, WebRequest request){
        ErrorMessage errorMessage =  new ErrorMessage(ex.getMessage(), LocalDate.now());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
