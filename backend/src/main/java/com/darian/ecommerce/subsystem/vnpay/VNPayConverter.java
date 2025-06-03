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

    // Build payment request
    protected VNPayRequest buildPaymentRequest(Long orderId, Float amount, String content) {
        return VNPayRequest.builder()
                .orderId(orderId)
                .amount(amount)
                .content(content)
                .transactionType(TransactionType.PAYMENT)
                .bankCode("VNB") // Default to domestic bank
                .language("vn")     // Default to Vietnamese
                .build();
    }

    // Build refund request
    protected VNPayRequest buildRefundRequest(Long orderId) {
        return VNPayRequest.builder()
                .orderId(orderId)
                .transactionType(TransactionType.REFUND)
                .build();
    }

    // Build payment request with custom bank code
    protected VNPayRequest buildPaymentRequest(Long orderId, Float amount, String content, String bankCode, String language) {
        return VNPayRequest.builder()
                .orderId(orderId)
                .amount(amount)
                .content(content)
                .transactionType(TransactionType.PAYMENT)
                .bankCode(bankCode)
                .language(language)
                .build();
    }
}
