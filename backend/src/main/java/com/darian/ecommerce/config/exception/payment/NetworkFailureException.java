package com.darian.ecommerce.config.exception.payment;

public class NetworkFailureException extends ConnectionException {
    public NetworkFailureException(String message) {
        super(message);
    }
}
