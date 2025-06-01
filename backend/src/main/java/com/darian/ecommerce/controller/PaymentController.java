package com.darian.ecommerce.controller;

import com.darian.ecommerce.config.exception.order.OrderNotFoundException;
import com.darian.ecommerce.config.exception.payment.InvalidPaymentMethodException;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.enums.PaymentMethod;
import com.darian.ecommerce.service.PaymentService;
import com.darian.ecommerce.subsystem.vnpay.VNPayResponseHandler;
import com.darian.ecommerce.utils.ApiEndpoints;
import com.darian.ecommerce.utils.LoggerMessages;
import com.darian.ecommerce.utils.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoints.PAYMENTS)
public class PaymentController {

    // Cohesion: Functional Cohesion
    // → Class chỉ phục vụ xử lý HTTP request liên quan đến thanh toán (pay/refund), không chứa logic nghiệp vụ.

    // SRP: Không vi phạm
    // → Lớp này chỉ đóng vai trò cầu nối giữa HTTP request và service xử lý logic thanh toán.

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    // Constructor injection for PaymentServiceImpl
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Process payment for an order
    @PostMapping(ApiEndpoints.PAYMENT_BY_ORDER)
    public ResponseEntity<PaymentResult> payOrder(@PathVariable Long orderId,
                                                  @RequestParam String paymentMethod) throws InvalidPaymentMethodException, OrderNotFoundException {
        // Validate payment method
        try {
            PaymentMethod.valueOf(paymentMethod.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPaymentMethodException(String.format(ErrorMessages.PAYMENT_INVALID_METHOD, paymentMethod));
        }
        log.info(LoggerMessages.PAYMENT_INIT, orderId, paymentMethod);
        PaymentResult result = paymentService.payOrder(orderId, paymentMethod);
        log.info(LoggerMessages.PAYMENT_COMPLETED, orderId, result.getPaymentStatus());
        return ResponseEntity.ok(result);
    }

    // Process refund for an order
    @PostMapping(ApiEndpoints.PAYMENT_REFUND)
    public ResponseEntity<RefundResult> processRefund(@PathVariable Long orderId) {
        log.info(LoggerMessages.PAYMENT_INIT, orderId, "refund");
        RefundResult result = paymentService.processRefund(orderId);
        log.info(LoggerMessages.PAYMENT_COMPLETED, orderId, result.getRefundStatus());
        return ResponseEntity.ok(result);
    }
}
