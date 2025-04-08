package com.darian.ecommerce.config.exception.payment;

public class CardBlockedException extends PaymentException {
    public CardBlockedException(String message) {
        super(message);
    }
}
