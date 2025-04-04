package com.darian.ecommerce.config;

public class InvalidOrderStatusException extends PaymentException {
  public InvalidOrderStatusException(String message) {
    super(message);
  }
}
