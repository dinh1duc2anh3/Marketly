package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Component;

@Component
public class VNPaySubsystemController  {
    private final VNPaySubsystemService vnPaySubsystemService;

    // Constructor injection
    public VNPaySubsystemController(VNPaySubsystemService vnPaySubsystemService) {
        this.vnPaySubsystemService = vnPaySubsystemService;
    }

    // Process payment through subsystem
    public PaymentResult processPayment(Long orderId, Float amount, String transactionContent) {
        return vnPaySubsystemService.executePayment(orderId, amount, transactionContent);
    }

    // Process refund through subsystem
    public RefundResult processRefund(Long orderId) {
        return vnPaySubsystemService.executeRefund(orderId);
    }
}
