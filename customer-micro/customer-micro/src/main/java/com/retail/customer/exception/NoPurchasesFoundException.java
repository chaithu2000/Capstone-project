package com.retail.customer.exception;

public class NoPurchasesFoundException extends RuntimeException {
    public NoPurchasesFoundException(String message) {
        super(message);
    }
}
