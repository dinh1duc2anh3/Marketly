package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundResult extends BasePaymentResult{
    private String refundStatus;
    private Date refundDate;
}
