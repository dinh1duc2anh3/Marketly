package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.enums.RefundStatus;
import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VNPayConverter {
    // Cohesion: Functional Cohesion
    // → Class này tập trung 100% vào việc tạo VNPayRequest từ dữ liệu domain, không làm nhiệm vụ nào khác.

    // SRP: Không vi phạm
    // → Lớp chỉ đảm nhiệm việc xây dựng request (builder cho transaction).

    // Convert payment details to VNPayRequest
    protected VNPayRequest buildPaymentRequest(Long orderId, Float amount, String content) {
        VNPayRequest request = new VNPayRequest();
        return VNPayRequest.builder()
                .orderId(orderId)
                .amount(amount)
                .transactionContent(content)
                .returnUrl("http://localhost:8080/payments/callback")
                .transactionType(TransactionType.PAYMENT)
                .build();
    }

    protected VNPayRequest buildRefundRequest(Long orderId) {
        VNPayRequest request = new VNPayRequest();
        return VNPayRequest.builder()
                .orderId(orderId)
                .amount(0f)
                .transactionContent("Refund for " + orderId)
                .returnUrl("http://localhost:8080/payments/callback")
                .transactionType(TransactionType.REFUND)
                .build();
    }



}
