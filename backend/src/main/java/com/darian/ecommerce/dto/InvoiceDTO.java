package com.darian.ecommerce.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private String orderId;
    private float shippingFee;
    private float total;
}
