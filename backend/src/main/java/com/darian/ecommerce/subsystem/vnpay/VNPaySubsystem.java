package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.service.PaymentInterface;
import org.springframework.stereotype.Component;

@Component
public class VNPaySubsystem implements PaymentInterface {
    private final VNPaySubsystemController controller;

    public VNPaySubsystem(VNPaySubsystemController controller) {
        this.controller = controller;
    }

    @Override
    public PaymentResult processPayment(String orderId, float amount, String transactionContent) {
        return controller.processPayment(orderId, amount, transactionContent);
    }

    @Override
    public RefundResult processRefund(String orderId) {
        return controller.processRefund(orderId);
    }

}
