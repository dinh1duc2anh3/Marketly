package com.darian.ecommerce.config;

public class SystemFailureException extends ConnectionException {
    public SystemFailureException(String message) {
        super(message);
    }
}
