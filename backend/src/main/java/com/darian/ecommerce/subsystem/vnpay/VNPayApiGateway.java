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
    // Cohesion: Functional Cohesion
    // → Tất cả method đều hướng tới một chức năng duy nhất là gọi API (gửi payment/refund request).

    // SRP: Không vi phạm
    // → Class chỉ chịu trách nhiệm trung gian kết nối giữa hệ thống và VNPay (abstract API calls, thêm logging, xử lý connection error).

    // Suggestion:
    // → Có thể đổi tên thành `VNPayClientGateway` nếu sau này hỗ trợ nhiều nhà cung cấp (ZaloPay, Momo), để phù hợp vai trò Gateway Pattern.

    private static final Logger logger = LoggerFactory.getLogger(VNPayApiGateway.class);

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
