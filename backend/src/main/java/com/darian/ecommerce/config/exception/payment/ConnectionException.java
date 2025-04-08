package com.darian.ecommerce.config.exception.payment;

public class ConnectionException extends RuntimeException {
    // Exception for connection issues with external payment systems
    public ConnectionException(String message) {
        super(message);
    }
}
