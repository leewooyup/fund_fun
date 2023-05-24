package com.fundfun.fundfund.exception;

public class InSufficientMoneyException extends RuntimeException {
    public InSufficientMoneyException(String message) {
        super(message);
    }
}
