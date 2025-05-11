package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import org.springframework.stereotype.Component;

@Component
public class VNPayAPI {
    // Simulate payment processing
    protected VNPayResponse simulatePayment(VNPayRequest request) {
        VNPayResponse response = new VNPayResponse();
        response.setTransactionId("TXN-" + System.currentTimeMillis());
        response.setStatus(VNPayResponseStatus.SUCCESS); // Simulated success
        response.setTransactionType(request.getTransactionType());
        return response;
    }

    // Simulate refund processing
    protected VNPayResponse simulateRefund(VNPayRequest request) {
        VNPayResponse response = new VNPayResponse();
        response.setTransactionId("TXN-" + System.currentTimeMillis());
        response.setStatus(VNPayResponseStatus.SUCCESS); // Simulated success
        response.setTransactionType(request.getTransactionType());
        return response;
    }
}
