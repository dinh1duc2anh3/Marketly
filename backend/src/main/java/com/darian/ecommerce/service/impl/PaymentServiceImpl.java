package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.config.exception.payment.PaymentException;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.repository.PaymentTransactionRepository;
import com.darian.ecommerce.service.AuditLogService;
import com.darian.ecommerce.service.OrderService;
import com.darian.ecommerce.service.PaymentService;
import com.darian.ecommerce.subsystem.vnpay.VNPaySubsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentTransactionRepository paymentRepository;
    private final VNPaySubsystem vnPaySubsystem;
    private final OrderService orderService;
    private final AuditLogService auditLogService;

    // Constructor injection for dependencies
    public PaymentServiceImpl(PaymentTransactionRepository paymentRepository,
                              VNPaySubsystem vnPaySubsystem,
                              OrderService orderService,
                              AuditLogService auditLogService) {
        this.paymentRepository = paymentRepository;
        this.vnPaySubsystem = vnPaySubsystem;
        this.orderService = orderService;
        this.auditLogService = auditLogService;
    }

    @Override
    public PaymentResult payOrder(Long orderId, String paymentMethod) {
        logger.info("Processing payment for order {} with method {}", orderId, paymentMethod);
        if (!validatePayment(orderId)) {
            logger.error("Payment validation failed for order {}", orderId);
            throw new PaymentException("Invalid payment details for order: " + orderId);
        }
        PaymentResult result = vnPaySubsystem.processPayment(orderId, 1000.0f, "Payment for order " + orderId);
        if ("SUCCESS".equals(result.getPaymentStatus())) {
            orderService.updatePaymentStatus(orderId, "PAID");
            logger.info("Payment successful for order {}", orderId);
        } else {
            logger.warn("Payment failed for order {}: {}", orderId, result.getErrorMessage());
        }
        auditLogService.logPayment(result); // Assuming PaymentResult is compatible with logPayment
        return result;
    }

    @Override
    public Boolean validatePayment(Long orderId) {
        logger.info("Validating payment for order {}", orderId);
        //need more check
        // Example validation: check if order exists and is in a payable state
        return orderService.checkCancellationValidity(orderId); // Delegate to OrderService
    }

    @Override
    public RefundResult processRefund(Long orderId) {
        logger.info("Processing refund for order {}", orderId);
        if (!checkCancellationValidity(orderId)) {
            logger.error("Refund not valid for order {}", orderId);
            throw new PaymentException("Refund not allowed for order: " + orderId);
        }
        RefundResult result = vnPaySubsystem.processRefund(orderId);
        if ("SUCCESS".equals(result.getRefundStatus())) {
            orderService.updatePaymentStatus(orderId, "REFUNDED");
            logger.info("Refund successful for order {}", orderId);
        } else {
            logger.warn("Refund failed for order {}: {}", orderId, result.getErrorMessage());
        }
        auditLogService.logPayment(result); // Assuming RefundResult is compatible with logPayment
        return result;
    }

    @Override
    public Boolean checkCancellationValidity(Long orderId) {
        logger.info("Checking cancellation validity for order {}", orderId);
        return orderService.checkCancellationValidity(orderId); // Delegate to OrderService
    }
}
