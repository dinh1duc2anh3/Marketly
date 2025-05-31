package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.exception.payment.ConnectionException;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import com.darian.ecommerce.utils.ApiEndpoints;
import com.darian.ecommerce.utils.LoggerMessages;
import com.darian.ecommerce.utils.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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

    @Retryable(
        value = {ConnectionException.class},
        maxAttempts = Constants.MAX_PAYMENT_RETRIES,
        backoff = @Backoff(delay = Constants.PAYMENT_RETRY_DELAY_MS)
    )
    public VNPayResponse sendPaymentRequest(VNPayRequest request) {
        logger.info(LoggerMessages.VNPAY_SENDING_REQUEST, request.getOrderId());
        try {
            VNPayResponse response = vnPayAPI.simulatePayment(request);
            logger.info(LoggerMessages.VNPAY_PAYMENT_EXECUTED, request.getOrderId(), response.getStatus());
            return response;
        } catch (Exception e) {
            logger.error(LoggerMessages.VNPAY_CONNECTION_ERROR, e.getMessage());
            throw new ConnectionException(ErrorMessages.VNPAY_CONNECTION_ERROR);
        }
    }

    @Retryable(
        value = {ConnectionException.class},
        maxAttempts = Constants.MAX_PAYMENT_RETRIES,
        backoff = @Backoff(delay = Constants.PAYMENT_RETRY_DELAY_MS)
    )
    public VNPayResponse sendRefundRequest(VNPayRequest request) {
        logger.info(LoggerMessages.VNPAY_SENDING_REQUEST, request.getOrderId());
        try {
            VNPayResponse response = vnPayAPI.simulateRefund(request);
            logger.info(LoggerMessages.VNPAY_REFUND_EXECUTED, orderId, response.getStatus());
            return response;
        } catch (Exception e) {
            logger.error(LoggerMessages.VNPAY_CONNECTION_ERROR, e.getMessage());
            throw new ConnectionException(ErrorMessages.VNPAY_CONNECTION_ERROR);
        }
    }
}
