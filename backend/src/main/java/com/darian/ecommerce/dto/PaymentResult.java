package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
