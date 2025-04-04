package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult extends BasePaymentResult {
    // Total amount of the payment
    private float totalAmount;

    // Content or description of the transaction
    private String transactionContent;

    // Date of the payment transaction
    private Date transactionDate;

    // Status of the payment (e.g., SUCCESS, FAILED)
    private String paymentStatus;
}
