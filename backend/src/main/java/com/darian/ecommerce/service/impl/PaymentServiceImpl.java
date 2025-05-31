package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.config.exception.order.OrderNotFoundException;
import com.darian.ecommerce.config.exception.payment.PaymentException;
import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.entity.Order;
import com.darian.ecommerce.enums.OrderStatus;
import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.repository.PaymentTransactionRepository;
import com.darian.ecommerce.service.AuditLogService;
import com.darian.ecommerce.service.OrderService;
import com.darian.ecommerce.service.PaymentService;
import com.darian.ecommerce.subsystem.vnpay.VNPaySubsystem;
import com.darian.ecommerce.utils.ErrorMessages;
import com.darian.ecommerce.utils.LoggerMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    // Cohesion: Functional Cohesion
    // → Lớp này tập trung xử lý logic thanh toán và hoàn tiền (gọi subsystem, cập nhật trạng thái đơn hàng, log, validate...).

    // SRP: Vi phạm nhẹ
    // → Dù các method đều liên quan đến payment, nhưng gồm cả `validate`, `checkCancellationValidity`, `refund`, `pay`
    // → có thể tách nhỏ thành PaymentProcessor, RefundProcessor nếu phức tạp tăng lên.


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
    public PaymentResult payOrder(Long orderId, String paymentMethod) throws OrderNotFoundException {
        logger.info(LoggerMessages.PAYMENT_PROCESSING, orderId, paymentMethod);

        Order order = orderService.findOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, orderId)));

        Float total = order.getTotal();
        if (!validatePayment(order)) {
            logger.error(LoggerMessages.PAYMENT_VALIDATION_FAILED, orderId);
            throw new PaymentException(String.format(ErrorMessages.PAYMENT_INVALID_STATUS, orderId));
        }

        PaymentResult result = vnPaySubsystem.processPayment(orderId, total, "Payment for order " + orderId);
        if ("SUCCESS".equals(result.getPaymentStatus())) {
            orderService.updatePaymentStatus(orderId, PaymentStatus.PAID);
            logger.info(LoggerMessages.PAYMENT_COMPLETED, orderId, result.getPaymentStatus());
        } else {
            logger.warn(LoggerMessages.PAYMENT_FAILED, orderId, result.getErrorMessage());
        }
        auditLogService.logPayment(result);
        return result;
    }

    @Override
    public Boolean validatePayment(Order order) throws OrderNotFoundException {
        Long orderId = order.getOrderId();
        OrderStatus orderStatus = order.getOrderStatus();
        PaymentStatus paymentStatus = order.getPaymentStatus();

        logger.info(LoggerMessages.VALIDATION_STARTED, "payment for order " + orderId);

        if (orderStatus == OrderStatus.PENDING) {
            orderService.updateOrderStatus(orderId, OrderStatus.CONFIRMED);
        }

        if (orderStatus != OrderStatus.CONFIRMED) {
            logger.warn(LoggerMessages.VALIDATION_FAILED, "order status", 
                String.format(ErrorMessages.ORDER_INVALID_STATUS, orderStatus));
            return false;
        }

        if (paymentStatus != PaymentStatus.UNPAID) {
            logger.warn(LoggerMessages.VALIDATION_FAILED, "payment status", 
                String.format(ErrorMessages.PAYMENT_ALREADY_PROCESSED, orderId));
            return false;
        }

        logger.info(LoggerMessages.VALIDATION_COMPLETED, "payment validation", true);
        return true;
    }

    @Override
    public RefundResult processRefund(Long orderId) {
        logger.info(LoggerMessages.REFUND_PROCESSING, orderId, "refund");
        if (!checkCancellationValidity(orderId)) {
            logger.error(LoggerMessages.VALIDATION_FAILED, "refund", orderId);
            throw new PaymentException(String.format(ErrorMessages.ORDER_CANNOT_BE_MODIFIED, orderId));
        }
        RefundResult result = vnPaySubsystem.processRefund(orderId);
        if ("SUCCESS".equals(result.getRefundStatus())) {
            orderService.updatePaymentStatus(orderId, PaymentStatus.REFUNDED);
            logger.info(LoggerMessages.REFUND_COMPLETED, orderId, result.getRefundStatus());
        } else {
            logger.warn(LoggerMessages.REFUND_FAILED, orderId, result.getErrorMessage());
        }
        auditLogService.logPayment(result);
        return result;
    }

    @Override
    public Boolean checkCancellationValidity(Long orderId) {
        logger.info(LoggerMessages.VALIDATION_STARTED, "cancellation for order " + orderId);
        return orderService.checkCancellationValidity(orderId);
    }
}
