package com.darian.ecommerce.config;

public class NetworkFailureException extends ConnectionException {
    public NetworkFailureException(String message) {
        super(message);
    }
}
