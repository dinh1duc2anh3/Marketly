package com.darian.ecommerce.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VNPayResponse {
    private String status;
    private String transactionId;
    private String errorMessage;
    private String transactionType;
}
