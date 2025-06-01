package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.exception.payment.ConnectionException;
import com.darian.ecommerce.config.exception.payment.PaymentProcessingException;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.config.exception.payment.ConnectionException;
import com.darian.ecommerce.utils.LoggerMessages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VNPaySubsystemService {
    // Cohesion: Functional Cohesion
    // → Các method đều xử lý một tác vụ cụ thể là "thực thi quy trình thanh toán hoặc hoàn tiền qua VNPay", từ build request → gửi → xử lý response.

    // SRP: Không vi phạm
    // → Lớp này đảm nhiệm một use case cụ thể (payment flow). Tuy chứa nhiều thành phần, nhưng tất cả đều phục vụ một mục đích duy nhất

    // Suggestion:
    // → Nếu logic ngày càng phức tạp, có thể tách `processPayment()` và `processRefund()` thành các Service riêng như `VNPayPaymentService` và `VNPayRefundService` để tăng modularity.


    private static final Logger log = LoggerFactory.getLogger(VNPaySubsystemService.class);

    private final VNPayConverter converter;
    private final VNPayApiGateway apiGateway;
    private final VNPayResponseHandler responseHandler;

    // Constructor injection
    protected VNPaySubsystemService( VNPayConverter converter, VNPayApiGateway apiGateway, VNPayResponseHandler responseHandler) {
        this.converter = converter;
        this.apiGateway = apiGateway;
        this.responseHandler = responseHandler;
    }

    // Execute payment via VNPay
    protected PaymentResult processPayment(Long orderId, Float amount, String content) {
        log.info(LoggerMessages.PAYMENT_PROCESSING, orderId, "VNPay");
        VNPayRequest request = converter.buildPaymentRequest(orderId, amount, content);
        VNPayResponse response = apiGateway.sendPaymentRequest(request);
        PaymentResult result = responseHandler.toPaymentResult(response);
        result.setTotalAmount(amount);
        result.setTransactionContent(content);

        log.info(LoggerMessages.PAYMENT_COMPLETED, orderId, result.getPaymentStatus());
        return result;
    }

    // Execute refund via VNPay
    protected RefundResult processRefund(Long orderId) {
        log.info(LoggerMessages.REFUND_PROCESSING, orderId, "VNPay");
        VNPayRequest request = converter.buildRefundRequest(orderId);
        VNPayResponse response = apiGateway.sendRefundRequest(request);
        log.info(LoggerMessages.REFUND_COMPLETED, orderId, response.getStatus());
        return responseHandler.toRefundResult(response);
    }
}
