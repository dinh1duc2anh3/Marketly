package com.darian.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long orderId;
    private Float shippingFee;
    private Float total;
}
