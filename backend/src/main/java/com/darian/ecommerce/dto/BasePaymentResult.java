package com.darian.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePaymentResult {
    // Order ID associated with the transaction
    private String orderId;

    // Type of transaction (PAY or REFUND)
    private String transactionType;

    // Unique transaction ID
    private String transactionId;

    // Error message if the transaction fails
    private String errorMessage;
}
