package com.darian.ecommerce.config.exception.payment;

public class InvalidOtpException extends PaymentException {
    public InvalidOtpException(String message) {
        super(message);
    }
}
