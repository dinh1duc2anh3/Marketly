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
    // Convert payment details to VNPayRequest
    public VNPayRequest toVNPayRequest(Long orderId, Float amount, String transactionContent, TransactionType transactionType) {
        VNPayRequest request = new VNPayRequest();
        request.setOrderId(orderId);
        request.setAmount(amount);
        request.setTransactionContent(transactionContent);
        request.setReturnUrl("http://localhost:8080/payments/callback");
        request.setTransactionType(transactionType);
        return request;
    }

    // Convert VNPayResponse to PaymentResult
    public PaymentResult fromVNPayResponse(VNPayResponse response) {
        PaymentResult result = new PaymentResult();
        result.setTransactionId(response.getTransactionId());
        if (response.getStatus().toString().equals(VNPayResponseStatus.SUCCESS)){
            result.setPaymentStatus(PaymentStatus.PAID);
        }
        else if (response.getStatus().toString().equals(VNPayResponseStatus.FAILURE)){
            result.setPaymentStatus(PaymentStatus.FAILED);
        }
        result.setErrorMessage(response.getErrorMessage());
        result.setTransactionType(response.getTransactionType());
        result.setTransactionContent("Payment"); // Placeholder
        result.setTransactionDate(LocalDateTime.now());
        result.setTotalAmount(0F); // Amount not in response, set elsewhere
        return result;
    }

    // Convert VNPayResponse to RefundResult
    public RefundResult fromVNPayRefundResponse(VNPayResponse response) {
        RefundResult result = new RefundResult();
        result.setTransactionId(response.getTransactionId());
        if (response.getStatus().toString().equals(VNPayResponseStatus.SUCCESS)){
            result.setRefundStatus(RefundStatus.REFUNDED);
        }
        else if (response.getStatus().toString().equals(VNPayResponseStatus.FAILURE)){
            result.setRefundStatus(RefundStatus.FAILED);
        }
        result.setErrorMessage(response.getErrorMessage());
        result.setTransactionType(response.getTransactionType());
        result.setRefundDate(LocalDateTime.now());
        return result;
    }

}
