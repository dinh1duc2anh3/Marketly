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
public class BasePaymentResult {
    private TransactionType transactionType;
    private Long orderId;
    private String transactionId;
    private String errorMessage;
}
