package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VNPaySubsystemService {
    private static final Logger logger = LoggerFactory.getLogger(VNPaySubsystemService.class);

    private final VNPayClient vnPayClient;

    // Constructor injection
    public VNPaySubsystemService(VNPayClient vnPayClient) {
        this.vnPayClient = vnPayClient;
    }

    // Execute payment via VNPayClient
    public PaymentResult executePayment(String orderId, float amount, String transactionContent) {
        logger.info("Executing VNPay payment for order {}, amount {}", orderId, amount);
        PaymentResult result = vnPayClient.processPayment(orderId, amount, transactionContent);
        logger.info("Payment executed for order {}: status {}", orderId, result.getPaymentStatus());
        return result;
    }

    // Execute refund via VNPayClient
    public RefundResult executeRefund(String orderId) {
        logger.info("Executing VNPay refund for order {}", orderId);
        RefundResult result = vnPayClient.processRefund(orderId);
        logger.info("Refund executed for order {}: status {}", orderId, result.getRefundStatus());
        return result;
    }
}
