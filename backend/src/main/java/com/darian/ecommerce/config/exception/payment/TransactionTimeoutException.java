package com.darian.ecommerce.config.exception.payment;

public class TransactionTimeoutException extends PaymentException {
    public TransactionTimeoutException(String message) {
        super(message);
    }
}
