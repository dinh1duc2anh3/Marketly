package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefundResult extends BasePaymentResult{
    private RefundStatus refundStatus;
    private LocalDateTime refundDate;
}
