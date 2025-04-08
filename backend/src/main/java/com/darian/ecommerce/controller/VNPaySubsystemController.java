package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.service.PaymentInterface;
import com.darian.ecommerce.service.VNPaySubsystemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vnpay-subsystem")
public class VNPaySubsystemController implements PaymentInterface {
    private VNPaySubsystemService vnPaySubsystemService;

    @Override
    public PaymentResult processPayment(String orderId, float amount, String transactionContent) {
        return null;
    }

    @Override
    public RefundResult processRefund(String orderId) {
        return null;
    }
}
