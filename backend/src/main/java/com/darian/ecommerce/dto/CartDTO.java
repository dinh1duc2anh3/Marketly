package com.darian.ecommerce.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    // ID of the user owning the cart
    private String userId;

    // List of items in the cart
    private List<CartItemDTO>  items;
}
