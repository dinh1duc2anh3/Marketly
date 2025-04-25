package com.darian.ecommerce.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long orderId;
    private Float shippingFee;
    private Float total;
}
