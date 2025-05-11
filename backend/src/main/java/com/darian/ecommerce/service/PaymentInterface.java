package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Service;

@Service
public interface PaymentInterface {
    // Cohesion: Functional Cohesion
// → Interface này chỉ cung cấp các hành vi liên quan đến xử lý giao dịch thanh toán và hoàn tiền.

// SRP: Không vi phạm
// → Interface này chỉ định nghĩa hợp đồng cho các hành vi thanh toán và hoàn tiền, không thực hiện thêm nhiệm vụ nào khác.

    // Process payment with amount and content
    public PaymentResult processPayment(Long orderId, Float amount, String transactionContent );

    // Process refund for an order
    public RefundResult processRefund(Long orderId);
}
