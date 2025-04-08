package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;

public class VNPayAPI {
    // Simulate payment processing
    public VNPayResponse simulatePayment(VNPayRequest request) {
        VNPayResponse response = new VNPayResponse();
        response.setTransactionId("TXN-" + System.currentTimeMillis());
        response.setStatus("SUCCESS"); // Simulated success
        response.setTransactionType(request.getTransactionType());
        return response;
    }

    // Simulate refund processing
    public VNPayResponse simulateRefund(VNPayRequest request) {
        VNPayResponse response = new VNPayResponse();
        response.setTransactionId("TXN-" + System.currentTimeMillis());
        response.setStatus("SUCCESS"); // Simulated success
        response.setTransactionType(request.getTransactionType());
        return response;
    }
}
