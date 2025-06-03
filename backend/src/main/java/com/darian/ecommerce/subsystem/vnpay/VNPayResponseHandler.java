package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.exception.payment.PaymentProcessingException;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.enums.RefundStatus;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import com.darian.ecommerce.service.impl.CategoryServiceImpl;
import com.darian.ecommerce.utils.ErrorMessages;
import com.darian.ecommerce.utils.LoggerMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VNPayResponseHandler {
    private static final Logger log = LoggerFactory.getLogger(VNPayResponseHandler.class);
    // Cohesion: Functional Cohesion
    // → Mọi method đều xử lý kết quả trả về từ VNPay (convert response → result object).

    // SRP: Không vi phạm
    // → Class này chỉ xử lý mapping giữa `VNPayResponse` và kết quả nghiệp vụ (PaymentResult, RefundResult).

    // Convert VNPayResponse to PaymentResult
    protected PaymentResult toPaymentResult(VNPayResponse response) {
        log.info(LoggerMessages.VNPAY_PAYMENT_EXECUTED, response.getTransactionId());
        if (response.getStatus().equals(VNPayResponseStatus.FAILURE)) {
            throw new PaymentProcessingException(String.format(ErrorMessages.VNPAY_TRANSACTION_FAILED, response.getMessage()));
        }
        return PaymentResult.builder()
                .transactionId(response.getTransactionId())
                .transactionType(response.getTransactionType())
                .transactionContent("Payment for " + response.getTransactionId())
                .transactionDate(LocalDateTime.now())
                .totalAmount(response.getAmount() != null ? response.getAmount().floatValue() : 0F)
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();
    }

    // Convert VNPayResponse to RefundResult
    protected RefundResult toRefundResult(VNPayResponse response) {
        log.info(LoggerMessages.VNPAY_REFUND_EXECUTED, response.getTransactionId());
        if (response.getStatus().equals(VNPayResponseStatus.FAILURE)) {
            throw new PaymentProcessingException(String.format(ErrorMessages.VNPAY_REFUND_FAILED, response.getMessage()));
        }
        return RefundResult.builder()
                .transactionId(response.getTransactionId())
                .transactionType(response.getTransactionType())
                .refundDate(LocalDateTime.now())
                .refundStatus(RefundStatus.REFUNDED)
                .build();
    }
}
