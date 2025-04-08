package com.darian.ecommerce.config.exception.payment;

public class CardNotRegisteredException extends PaymentException {
    public CardNotRegisteredException(String message) {
        super(message);
    }
}
