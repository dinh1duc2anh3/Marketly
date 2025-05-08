package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.PaymentStatus;
import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VNPayResponse {
    private VNPayResponseStatus status;
    private String transactionId;
    private String errorMessage;
    private TransactionType transactionType;
}
