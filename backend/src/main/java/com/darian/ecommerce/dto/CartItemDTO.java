package com.darian.ecommerce.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    // ID of the product
    private Long productId;

    // Quantity of the product in the cart
    private Integer quantity;
}
