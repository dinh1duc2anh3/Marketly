package com.darian.ecommerce.config.exception.payment;

public class RefundFailedException extends PaymentProcessingException {
    public RefundFailedException(String message) {
        super(message);
    }
}
