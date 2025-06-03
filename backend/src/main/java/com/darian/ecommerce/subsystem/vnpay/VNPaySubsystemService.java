package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.config.VNPayConfig;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.config.exception.payment.ConnectionException;
import com.darian.ecommerce.utils.LoggerMessages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VNPaySubsystemService {
    // Cohesion: Functional Cohesion
    // → Các method đều xử lý một tác vụ cụ thể là "thực thi quy trình thanh toán hoặc hoàn tiền qua VNPay", từ build request → gửi → xử lý response.

    // SRP: Không vi phạm
    // → Lớp này đảm nhiệm một use case cụ thể (payment flow). Tuy chứa nhiều thành phần, nhưng tất cả đều phục vụ một mục đích duy nhất


    private static final Logger log = LoggerFactory.getLogger(VNPaySubsystemService.class);

    private final VNPayAPI vnPayAPI;
    private final VNPayConfig vnPayConfig;
    private final VNPayConverter converter;
    private final VNPayResponseHandler responseHandler;
    private final VNPayApiGateway apiGateway;
    
    public VNPaySubsystemService(VNPayAPI vnPayAPI, VNPayConfig vnPayConfig, VNPayConverter converter, VNPayResponseHandler responseHandler, VNPayApiGateway apiGateway) {
        this.vnPayAPI = vnPayAPI;
        this.vnPayConfig = vnPayConfig;
        this.converter = converter;
        this.responseHandler = responseHandler;
        this.apiGateway = apiGateway;
    }

    public String createPaymentUrl(VNPayRequest request) {
        request.setTransactionType(TransactionType.PAYMENT);
        return vnPayAPI.createPaymentUrl(request);
    }

    public PaymentResult processVNPayReturn(Map<String, String> vnpResponse) {
        PaymentResult result = new PaymentResult();
        
        String vnp_ResponseCode = vnpResponse.get("vnp_ResponseCode");
        String vnp_TransactionStatus = vnpResponse.get("vnp_TransactionStatus");
        String vnp_TxnRef = vnpResponse.get("vnp_TxnRef");
        String vnp_Amount = vnpResponse.get("vnp_Amount");
        String vnp_OrderInfo = vnpResponse.get("vnp_OrderInfo");
        
        // Verify checksum
        if (verifyChecksum(vnpResponse)) {
            if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                result.setPaymentStatus(PaymentStatus.SUCCESS);
            } else {
                result.setPaymentStatus(PaymentStatus.FAILED);
            }
        } else {
            result.setPaymentStatus(PaymentStatus.FAILED);
            log.error("Invalid checksum from VNPay response");
        }
        
        result.setTransactionId(vnp_TxnRef);
        result.setTotalAmount(Float.parseFloat(vnp_Amount) / 100); // Convert from VND cents to VND
        result.setTransactionContent(vnp_OrderInfo);
        
        return result;
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
    private PaymentStatus convertVNPayStatus(String vnpayStatus) {
        if ("00".equals(vnpayStatus)) {
            return PaymentStatus.SUCCESS;
        }
        return PaymentStatus.FAILED;
    }

    private boolean verifyChecksum(Map<String, String> fields) {
        // Implementation of checksum verification based on VNPay documentation
        // This should use the same HMAC SHA512 algorithm as in VNPayAPI
        return true; // TODO: Implement actual checksum verification
    }
}
