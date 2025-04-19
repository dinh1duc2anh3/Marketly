package com.darian.ecommerce.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VNPayRequest {
    private Float amount;
    private String transactionContent;
    private Long orderId;
    private String returnUrl;
    private String transactionType;
}
