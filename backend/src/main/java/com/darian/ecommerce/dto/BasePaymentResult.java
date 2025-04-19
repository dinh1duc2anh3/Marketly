package com.darian.ecommerce.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePaymentResult {
    private String transactionType;
    private Long orderId;
    private String transactionId;
    private String errorMessage;
}
