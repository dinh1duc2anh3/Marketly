package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.exception.payment.ConnectionException;
import com.darian.ecommerce.config.exception.payment.PaymentProcessingException;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VNPayClient {
    private static final Logger logger = LoggerFactory.getLogger(VNPayClient.class);
    private final VNPayConverter paymentConverter;
    private final VNPayConverter refundConverter;
    private final VNPayAPI vnPayAPI;

    // Constructor injection
    public VNPayClient(VNPayConverter paymentConverter, VNPayConverter refundConverter) {
        this.paymentConverter = paymentConverter;
        this.refundConverter = refundConverter;
        this.vnPayAPI = new VNPayAPI(); // Simulate external API
    }

    // Send payment request to VNPay API
    public VNPayResponse sendPaymentRequest(VNPayRequest request) {
        logger.info("Sending payment request to VNPay for order {}", request.getOrderId());
        try {
            return vnPayAPI.simulatePayment(request);
        } catch (Exception e) {
            logger.error("Failed to connect to VNPay API: {}", e.getMessage());
            throw new ConnectionException("Unable to connect to VNPay API");
        }
    }

    // Send refund request to VNPay API
    public VNPayResponse sendRefundRequest(VNPayRequest request) {
        logger.info("Sending refund request to VNPay for order {}", request.getOrderId());
        try {
            return vnPayAPI.simulateRefund(request);
        } catch (Exception e) {
            logger.error("Failed to connect to VNPay API: {}", e.getMessage());
            throw new ConnectionException("Unable to connect to VNPay API");
        }
    }

    // Process payment response from VNPay
    public PaymentResult processPaymentResponse(VNPayResponse response) {
        logger.info("Processing payment response: transactionId {}", response.getTransactionId());
        if ("FAILED".equals(response.getStatus())) {
            throw new PaymentProcessingException("Payment processing failed: " + response.getErrorMessage());
        }
        return paymentConverter.fromVNPayResponse(response);
    }

    // Process refund response from VNPay
    public RefundResult processRefundResponse(VNPayResponse response) {
        logger.info("Processing refund response: transactionId {}", response.getTransactionId());
        if ("FAILED".equals(response.getStatus())) {
            throw new PaymentProcessingException("Refund processing failed: " + response.getErrorMessage());
        }
        return refundConverter.fromVNPayRefundResponse(response);
    }

    // Helper method to process payment
    public PaymentResult processPayment(String orderId, float amount, String transactionContent) {
        VNPayRequest request = paymentConverter.toVNPayRequest(orderId, amount, transactionContent, "PAY");
        VNPayResponse response = sendPaymentRequest(request);
        return processPaymentResponse(response);
    }

    // Helper method to process refund
    public RefundResult processRefund(String orderId) {
        VNPayRequest request = paymentConverter.toVNPayRequest(orderId, 0, "Refund for " + orderId, "REFUND");
        VNPayResponse response = sendRefundRequest(request);
        return processRefundResponse(response);
    }
}
