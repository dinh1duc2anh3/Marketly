package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.RefundStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundResult extends BasePaymentResult{
    private RefundStatus refundStatus;
    private LocalDateTime refundDate;
}
