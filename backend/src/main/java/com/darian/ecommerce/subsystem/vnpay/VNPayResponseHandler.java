package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.exception.payment.PaymentProcessingException;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.enums.RefundStatus;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VNPayResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(VNPayResponseHandler.class);

    // Convert VNPayResponse to PaymentResult
    protected PaymentResult toPaymentResult(VNPayResponse response) {

        logger.info("Processing payment response: transactionId {}", response.getTransactionId());
        if (response.getStatus().equals(VNPayResponseStatus.FAILURE)) {
            throw new PaymentProcessingException("Payment processing failed: " + response.getErrorMessage());
        }
        return PaymentResult.builder()
                .transactionId(response.getTransactionId())
                .transactionType(response.getTransactionType())
                .transactionContent("Payment for"+response.getTransactionId())
                .transactionDate(LocalDateTime.now())
                .errorMessage(response.getErrorMessage())
                .totalAmount(0F)
                .paymentStatus(PaymentStatus.PAID)
                .build();
    }

    // Convert VNPayResponse to RefundResult
    protected RefundResult toRefundResult(VNPayResponse response) {
        logger.info("Processing refund response: transactionId {}", response.getTransactionId());
        if (response.getStatus().equals(VNPayResponseStatus.FAILURE)) {
            throw new PaymentProcessingException("Refund processing failed: " + response.getErrorMessage());
        }
        return RefundResult.builder()
                .transactionId(response.getTransactionId())
                .transactionType(response.getTransactionType())
                .refundDate(LocalDateTime.now())
                .errorMessage(response.getErrorMessage())
                .refundStatus(RefundStatus.REFUNDED )
                .build();
    }
}
