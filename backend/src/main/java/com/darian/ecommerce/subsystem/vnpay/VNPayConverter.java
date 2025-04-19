package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import org.springframework.stereotype.Component;

@Component
public class VNPayConverter {
    // Convert payment details to VNPayRequest
    public VNPayRequest toVNPayRequest(Long orderId, Float amount, String transactionContent, String transactionType) {
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
        result.setPaymentStatus(response.getStatus());
        result.setErrorMessage(response.getErrorMessage());
        result.setTransactionType(response.getTransactionType());
        result.setTransactionContent("Payment"); // Placeholder
        result.setTransactionDate(new java.util.Date());
        result.setTotalAmount(0); // Amount not in response, set elsewhere
        return result;
    }

    // Convert VNPayResponse to RefundResult
    public RefundResult fromVNPayRefundResponse(VNPayResponse response) {
        RefundResult result = new RefundResult();
        result.setTransactionId(response.getTransactionId());
        result.setRefundStatus(response.getStatus());
        result.setErrorMessage(response.getErrorMessage());
        result.setTransactionType(response.getTransactionType());
        result.setRefundDate(new java.util.Date());
        return result;
    }

}
