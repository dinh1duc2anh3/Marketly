package com.darian.ecommerce.config.exception.payment;

public class InvalidOrderStatusException extends PaymentException {
  public InvalidOrderStatusException(String message) {
    super(message);
  }
}
