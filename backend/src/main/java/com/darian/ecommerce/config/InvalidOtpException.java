package com.darian.ecommerce.config;

public class InvalidOtpException extends PaymentException {
    public InvalidOtpException(String message) {
        super(message);
    }
}
