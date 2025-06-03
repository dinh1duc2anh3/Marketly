package com.darian.ecommerce.strategy;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.subsystem.vnpay.VNPaySubsystemService;
import org.springframework.stereotype.Component;

@Component
public class CreditCardStrategy implements PaymentStrategy {
//    private final VNPaySubsystemService vnPayService;

    public CreditCardStrategy(VNPaySubsystemService vnPayService) {
//        this.vnPayService = vnPayService;
    }

    @Override
    public PaymentResult processPayment(Long orderId, Float amount, String content) {
//        VNPayRequest request = VNPayRequest.builder()
//                .orderId(orderId)
//                .amount(amount)
//                .content(content)
//                .transactionType(TransactionType.PAYMENT)
//                .bankCode("INTCARD") // International card code for VNPay
//                .build();
//
//        return vnPayService.processPayment(orderId, amount, content);
        return null;
    }

    @Override
    public PaymentResult processRefund(Long orderId) {
//        return vnPayService.processRefund(orderId);
        return null;
    }
}