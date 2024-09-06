package com.example.orderservice.exception;

public class MqJmsException extends RuntimeException{
    public MqJmsException(String message){
            super(message);
    }
}
