package com.darian.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePaymentResult {
    private String orderId;
    private String transactionId;
    private String errorMessage;
}
