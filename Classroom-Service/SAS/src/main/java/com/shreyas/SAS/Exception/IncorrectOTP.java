package com.shreyas.SAS.Exception;

public class IncorrectOTP extends RuntimeException{
    public IncorrectOTP(String message) {
        super(message);
    }
}
