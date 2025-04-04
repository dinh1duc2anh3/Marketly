package com.darian.ecommerce.config;

public class PaymentFailedException extends PaymentProcessingException {
    public PaymentFailedException(String message) {
        super(message);
    }
}
