package com.darian.ecommerce.config;

public class RefundFailedException extends PaymentProcessingException {
    public RefundFailedException(String message) {
        super(message);
    }
}
