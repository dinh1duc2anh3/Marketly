package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;

public interface PaymentService {
    // Process payment for an order with a specified payment method
    public PaymentResult payOrder(String orderId, String paymentMethod);

    // Validate payment details for an order
    public Boolean validatePayment(String orderId);

    // Process refund for an order
    public RefundResult processRefund(String orderId);

    // Check if cancellation is valid for an order
    public Boolean checkCancellationValidity(String orderId);
}
