package com.darian.ecommerce.config;

public class TransactionSuspiciousException extends PaymentException {
    public TransactionSuspiciousException(String message) {
        super(message);
    }
}
