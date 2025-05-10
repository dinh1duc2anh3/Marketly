package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult extends BasePaymentResult {
    private Float totalAmount;
    private String transactionContent;
    private LocalDateTime transactionDate;
    private PaymentStatus paymentStatus;
}
