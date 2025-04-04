package com.darian.ecommerce.config;

public class CardBlockedException extends PaymentException {
    public CardBlockedException(String message) {
        super(message);
    }
}
