package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;

public interface PaymentService {
    public PaymentResult payOrder(String orderId, String paymentMethod);

    public Boolean validatePayment(String orderId);

    public RefundResult processRefund(String orderId);

    public Boolean checkCancellationValidity(String orderId);
}
