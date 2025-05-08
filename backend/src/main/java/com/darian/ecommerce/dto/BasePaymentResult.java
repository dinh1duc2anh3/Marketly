package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.TransactionType;
import lombok.*;

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
