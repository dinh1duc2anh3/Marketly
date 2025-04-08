package com.darian.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDTO {
    private String productId;
    private int quantity;
    private float unitPrice;
    private float lineTotal;
}
