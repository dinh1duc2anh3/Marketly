package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.TransactionType;
import com.darian.ecommerce.enums.VNPayResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
