package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import com.darian.ecommerce.subsystem.vnpay.VNPayClient;
import org.springframework.stereotype.Service;

@Service
public class VNPaySubsystemService {
    private VNPayClient vnpayClient;

    public PaymentResult executePayment(Long orderId, Float amount, String transactionContent){
        return null;
    }

    public RefundResult executeRefund (Long orderId){
        return null;
    }
}
