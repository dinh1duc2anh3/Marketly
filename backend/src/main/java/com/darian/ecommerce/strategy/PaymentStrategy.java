package com.darian.ecommerce.strategy;

import com.darian.ecommerce.dto.PaymentResult;

public interface PaymentStrategy {
    PaymentResult processPayment(Long orderId, Float amount, String content);
    PaymentResult processRefund(Long orderId);
} 