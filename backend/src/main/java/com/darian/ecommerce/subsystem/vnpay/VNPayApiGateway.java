package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.exception.payment.ConnectionException;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VNPayApiGateway {
    private static final Logger logger = LoggerFactory.getLogger(VNPayClient.class);

    private final VNPayAPI vnPayAPI;

    protected VNPayApiGateway(VNPayAPI vnPayAPI) {
        this.vnPayAPI = vnPayAPI;
    }


    protected VNPayResponse sendPaymentRequest(VNPayRequest request) {

        logger.info("Sending payment request to VNPay for order {}", request.getOrderId());
        try {
            return vnPayAPI.simulatePayment(request);
        } catch (Exception e) {
            logger.error("Failed to connect to VNPay API: {}", e.getMessage());
            throw new ConnectionException("Unable to connect to VNPay API");
        }
    }


    // Send refund request to VNPay API
    protected VNPayResponse sendRefundRequest(VNPayRequest request) {
        logger.info("Sending refund request to VNPay for order {}", request.getOrderId());
        try {
            return vnPayAPI.simulateRefund(request);
        } catch (Exception e) {
            logger.error("Failed to connect to VNPay API: {}", e.getMessage());
            throw new ConnectionException("Unable to connect to VNPay API");
        }
    }

}
