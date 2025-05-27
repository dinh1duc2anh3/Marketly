package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.service.PaymentInterface;
import org.springframework.stereotype.Component;

@Component
public class VNPaySubsystem implements PaymentInterface {
    // Cohesion: Functional Cohesion
    // → Là entry-point chính của hệ thống thanh toán VNPay – chỉ delegate việc xử lý thanh toán/hoàn tiền.

    // SRP: Không vi phạm
    // → Class chịu trách nhiệm duy nhất là expose subsystem qua interface `PaymentInterface`.

    private final VNPaySubsystemAdapter adapter;

    public VNPaySubsystem(VNPaySubsystemAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public PaymentResult processPayment(Long orderId, Float amount, String transactionContent) {
        return adapter.processPayment(orderId, amount, transactionContent);
    }

    @Override
    public RefundResult processRefund(Long orderId) {
        return adapter.processRefund(orderId);
    }

}
