package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult extends BasePaymentResult {
    private float totalAmount;
    private String transactionContent;
    private Date transactionDate;
    private String paymentStatus;
}
