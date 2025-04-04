package com.darian.ecommerce.config;

public class CardNotRegisteredException extends PaymentException {
    public CardNotRegisteredException(String message) {
        super(message);
    }
}
