package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;

public interface PaymentInterface {
    public PaymentResult processPayment(float amount, String transactionContent );

    public RefundResult processRefund(String orderId);
}
