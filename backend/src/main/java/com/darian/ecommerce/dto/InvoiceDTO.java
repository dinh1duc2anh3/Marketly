package com.darian.ecommerce.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

    private Long orderId;
    private Float shippingFee;
    private Float total;
}
