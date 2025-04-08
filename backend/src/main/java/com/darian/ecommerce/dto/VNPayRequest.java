package com.darian.ecommerce.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VNPayRequest {
    private float amount;
    private String transactionContent;
    private String orderId;
    private String returnUrl;
    private String transactionType;
}
