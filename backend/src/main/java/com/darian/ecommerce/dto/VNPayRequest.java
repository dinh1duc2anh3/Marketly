package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VNPayRequest {
    private Float amount;
    private String transactionContent;
    private Long orderId;
    private String returnUrl;
    private TransactionType transactionType;
}
