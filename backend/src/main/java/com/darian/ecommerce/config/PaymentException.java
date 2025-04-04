package com.darian.ecommerce.config;

public class PaymentException extends RuntimeException {
  public PaymentException(String message) {
    super(message);
  }
}
