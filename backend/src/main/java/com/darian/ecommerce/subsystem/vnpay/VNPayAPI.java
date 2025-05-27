package com.darian.ecommerce.subsystem.vnpay;

import com.darian.ecommerce.dto.VNPayRequest;
import com.darian.ecommerce.dto.VNPayResponse;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import org.springframework.stereotype.Component;

@Component
public class VNPayAPI {
    // Cohesion: Functional Cohesion
    // → Mọi method trong class đều phục vụ chung một mục đích là "mô phỏng giao tiếp với VNPay" (payment/refund), không bị lệch nhiệm vụ.

    // SRP: Không vi phạm
    // → Lớp chỉ chịu trách nhiệm mô phỏng xử lý từ phía hệ thống VNPay (mock API behavior).
    // Tuy nhiên, tên class nên được đặt rõ hơn như `MockVNPayClient` để thể hiện rõ trách nhiệm mô phỏng.

    // Suggestion:
    // → Nếu hệ thống thực tế cần gọi API thật, tách riêng `VNPayAPI` thành interface, và để class này là `MockVNPayAPI implements VNPayAPI` để
    // dễ thay thế bằng real API sau này.

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
