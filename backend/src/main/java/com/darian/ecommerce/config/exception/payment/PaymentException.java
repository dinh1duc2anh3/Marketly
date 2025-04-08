package com.darian.ecommerce.config.exception.payment;

public class PaymentException extends RuntimeException {
    // Base exception for payment-related errors
    public PaymentException(String message) {
    super(message);
  }
}
