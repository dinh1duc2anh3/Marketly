package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class VNPaySubsystemAdapter {
    // Cohesion: Functional Cohesion
    // → Lớp này chỉ làm nhiệm vụ kết nối VNPaySubsystem với hệ thống chính bằng cách gọi các method nghiệp vụ đã được trừu tượng hóa.

    // SRP: Không vi phạm
    // → Là adapter đơn thuần theo đúng vai trò trong Adapter Pattern, không chứa logic xử lý.

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
