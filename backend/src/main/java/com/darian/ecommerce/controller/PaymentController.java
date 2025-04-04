package com.darian.ecommerce.controller;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.service.impl.PaymentServiceImpl;
import org.springframework.http.ResponseEntity;

public class PaymentController {
    private PaymentServiceImpl paymentService;

    public ResponseEntity<PaymentResult> payOrder(String orderId, String paymentMethod){

    }

    public ResponseEntity<RefundResult> processRefund(String orderId){

    }
}
