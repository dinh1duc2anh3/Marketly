package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;

public interface PaymentInterface {
    // Process payment with amount and content
    public PaymentResult processPayment(String orderId, float amount, String transactionContent );

    // Process refund for an order
    public RefundResult processRefund(String orderId);
}
