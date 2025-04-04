package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundResult extends BasePaymentResult{
    // Status of the refund (e.g., SUCCESS, FAILED)
    private String refundStatus;

    // Date of the refund transaction
    private Date refundDate;
}
