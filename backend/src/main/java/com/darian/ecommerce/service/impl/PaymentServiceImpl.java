package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.repository.PaymentTransactionRepository;
import com.darian.ecommerce.service.AuditLogService;
import com.darian.ecommerce.service.OrderService;
import com.darian.ecommerce.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {
    private PaymentTransactionRepository paymentRepository;
    private VNPaySubsystem vnpaySubsystem;
    private OrderService orderService;
    private AuditLogService auditLogService;

    @Override
    public PaymentResult payOrder(String orderId, String paymentMethod) {
        return null;
    }

    @Override
    public Boolean validatePayment(String orderId) {
        return null;
    }

    @Override
    public RefundResult processRefund(String orderId) {
        return null;
    }

    @Override
    public Boolean checkCancellationValidity(String orderId) {
        return null;
    }
}
