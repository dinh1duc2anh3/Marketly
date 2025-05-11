package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    //  Functional Cohesion: Class chỉ phục vụ duy nhất cho mục đích xử lý HTTP request liên quan đến thanh toán
    //  Không vi phạm SRP: Chỉ đóng vai trò cầu nối giữa request và service, không chứa logic xử lý nghiệp vụ

    // Constructor injection for PaymentServiceImpl
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Process payment for an order
    @PostMapping("/{orderId}/pay")
    public ResponseEntity<PaymentResult> payOrder(@PathVariable Long orderId,
                                                  @RequestParam String paymentMethod) {
        logger.info("Initiating payment for order {} with method {}", orderId, paymentMethod);
        PaymentResult result = paymentService.payOrder(orderId, paymentMethod);
        logger.info("Payment completed for order {}: status {}", orderId, result.getPaymentStatus());
        return ResponseEntity.ok(result);
    }

    // Process refund for an order
    @PostMapping("/{orderId}/refund")
    public ResponseEntity<RefundResult> processRefund(@PathVariable Long orderId) {
        logger.info("Initiating refund for order {}", orderId);
        RefundResult result = paymentService.processRefund(orderId);
        logger.info("Refund completed for order {}: status {}", orderId, result.getRefundStatus());
        return ResponseEntity.ok(result);
    }


}
