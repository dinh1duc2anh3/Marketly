package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    // Process payment for an order with a specified payment method
    public PaymentResult payOrder(Long orderId, String paymentMethod);

    // Validate payment details for an order
    public Boolean validatePayment(Long orderId);

    // Process refund for an order
    public RefundResult processRefund(Long orderId);

    // Check if cancellation is valid for an order
    public Boolean checkCancellationValidity(Long orderId);
}
