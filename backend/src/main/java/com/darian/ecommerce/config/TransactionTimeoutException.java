package com.darian.ecommerce.config;

public class TransactionTimeoutException extends PaymentException {
    public TransactionTimeoutException(String message) {
        super(message);
    }
}
