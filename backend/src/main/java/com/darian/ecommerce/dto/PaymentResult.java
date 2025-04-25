package com.darian.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult extends BasePaymentResult {
    private Float totalAmount;
    private String transactionContent;
    private LocalDateTime transactionDate;
    private String paymentStatus;
}
