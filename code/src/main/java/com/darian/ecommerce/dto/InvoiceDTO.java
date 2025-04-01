package com.darian.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private String orderId;
    private float shippingFee;
    private float total;
}
