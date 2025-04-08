package com.darian.ecommerce.config.exception.payment;

public class PaymentFailedException extends PaymentProcessingException {
    public PaymentFailedException(String message) {
        super(message);
    }
}
