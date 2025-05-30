package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.PaymentResult;
import com.darian.ecommerce.dto.RefundResult;
import org.springframework.stereotype.Service;

public interface PaymentService {

    // Cohesion: Functional Cohesion
    // → Interface chỉ định nghĩa các hành vi liên quan đến thanh toán và hoàn tiền.

    // SRP: Vi phạm nhẹ
    // → Gộp xử lý thanh toán và hoàn tiền vào cùng interface. Có thể chia thành `PaymentService` và `RefundService` để rõ ràng hơn.


    // Process payment for an order with a specified payment method
    public PaymentResult payOrder(Long orderId, String paymentMethod);

    // Validate payment details for an order
    public Boolean validatePayment(Long orderId);

//     Process refund for an order
    public RefundResult processRefund(Long orderId);

//     Check if cancellation is valid for an order
    public Boolean checkCancellationValidity(Long orderId);
}
