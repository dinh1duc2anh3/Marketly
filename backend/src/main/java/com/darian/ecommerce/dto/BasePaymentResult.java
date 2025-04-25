package com.darian.ecommerce.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePaymentResult {
    private String transactionType;
    private Long orderId;
    private String transactionId;
    private String errorMessage;
}
