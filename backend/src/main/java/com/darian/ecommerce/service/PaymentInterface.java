package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Service;

@Service
public interface PaymentInterface {
    // Process payment with amount and content
    public PaymentResult processPayment(Long orderId, Float amount, String transactionContent );

    // Process refund for an order
    public RefundResult processRefund(Long orderId);
}
