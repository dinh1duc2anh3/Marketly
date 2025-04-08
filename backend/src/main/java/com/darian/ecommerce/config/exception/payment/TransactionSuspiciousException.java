package com.darian.ecommerce.config.exception.payment;

public class TransactionSuspiciousException extends PaymentException {
    public TransactionSuspiciousException(String message) {
        super(message);
    }
}
