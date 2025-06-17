package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResDTO;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.service.PaymentInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public interface VNPaySubsystem implements PaymentInterface {
    // Cohesion: Functional Cohesion
    // → Là entry-point chính của hệ thống thanh toán VNPay – chỉ delegate việc xử lý thanh toán/hoàn tiền.

    // SRP: Không vi phạm
    // → Class chịu trách nhiệm duy nhất là expose subsystem qua interface `PaymentInterface`.

    //TODO: check xem xoa co on khong
//    private final VNPaySubsystemAdapter adapter;
//
//    public VNPaySubsystem(VNPaySubsystemAdapter adapter) {
//        this.adapter = adapter;
//    }
//
//    @Override
//    public PaymentResult processPayment(Long orderId, Float amount, String transactionContent) {
//        return adapter.processPayment(orderId, amount, transactionContent);
//    }
//
//    @Override
//    public RefundResult processRefund(Long orderId) {
//        return adapter.processRefund(orderId);
//    }

    PaymentResDTO initiatePayment(Long orderId, Float amount, String transactionContent, HttpServletRequest request);
    PaymentResult processPayment(Long orderId, Float amount, String transactionContent);
    RefundResult processRefund(Long orderId);
    PaymentResult handleIpnCallback(Map<String, String> vnpParams);
}
