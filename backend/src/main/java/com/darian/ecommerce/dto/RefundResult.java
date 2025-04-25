package com.darian.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundResult extends BasePaymentResult{
    private String refundStatus;
    private LocalDateTime refundDate;
}
