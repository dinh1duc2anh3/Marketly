package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.TransactionType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VNPayRequest {
    private Long orderId;
    private Float amount;
    private String content;
    private TransactionType transactionType; 
    private String bankCode;
    private String language;
    
    // Additional fields for refund
    private String transactionNo;
    private String transactionDate;
    private String createBy;
}
