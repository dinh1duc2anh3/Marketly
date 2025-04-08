package com.darian.ecommerce.config.exception.payment;

public class SystemFailureException extends ConnectionException {
    public SystemFailureException(String message) {
        super(message);
    }
}
