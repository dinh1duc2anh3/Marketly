package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Service;

@Service
public interface PaymentInterface {
    // Cohesion: Functional Cohesion
    // → Interface chỉ cung cấp các hành vi xử lý giao dịch thanh toán và hoàn tiền.

    // SRP: Không vi phạm
    // → Chỉ định nghĩa hợp đồng cho Payment Subsystem, không kiêm nhiệm thêm trách nhiệm nào khác.


    // Process payment with amount and content
    public PaymentResult processPayment(Long orderId, Float amount, String transactionContent );

    // Process refund for an order
    public RefundResult processRefund(Long orderId);
}
