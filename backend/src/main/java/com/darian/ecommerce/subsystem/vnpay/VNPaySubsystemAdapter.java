package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Component;

@Component
public class VNPaySubsystemAdapter {
// Cohesion: Functional Cohesion
// → Lớp này chỉ tập trung vào việc làm trung gian gọi các phương thức thanh toán và hoàn tiền từ subsystem VNPay.

// SRP: Không vi phạm
// → Lớp này chỉ đóng vai trò là adapter trung gian giữa hệ thống chính và VNPaySubsystemService.

    private final VNPaySubsystemService vnPaySubsystemService;

    // Constructor injection
    public VNPaySubsystemAdapter(VNPaySubsystemService vnPaySubsystemService) {
        this.vnPaySubsystemService = vnPaySubsystemService;
    }

    // Process payment through subsystem
    protected PaymentResult processPayment(Long orderId, Float amount, String transactionContent) {
        return vnPaySubsystemService.processPayment(orderId, amount, transactionContent);
    }

    // Process refund through subsystem
    protected RefundResult processRefund(Long orderId) {
        return vnPaySubsystemService.processRefund(orderId);
    }
}
