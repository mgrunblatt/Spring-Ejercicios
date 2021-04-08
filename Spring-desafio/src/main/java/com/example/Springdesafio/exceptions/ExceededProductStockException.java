package com.example.Springdesafio.exceptions;

public class ExceededProductStockException extends Exception {

    public ExceededProductStockException (String message){
        super(message);
    }
}
