package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
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
