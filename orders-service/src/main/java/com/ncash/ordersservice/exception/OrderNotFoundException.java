package com.ncash.ordersservice.exception;

public class OrderNotFoundException extends  RuntimeException{
    public OrderNotFoundException(String message){
        super(message);
    }
}
