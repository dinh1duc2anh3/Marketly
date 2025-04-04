package com.darian.ecommerce.config;

public class TransactionFailureException extends PaymentException {
    public TransactionFailureException(String message) {
        super(message);
    }
}
