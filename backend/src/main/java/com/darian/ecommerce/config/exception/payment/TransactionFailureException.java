package com.darian.ecommerce.config.exception.payment;

public class TransactionFailureException extends PaymentException {
    public TransactionFailureException(String message) {
        super(message);
    }
}
