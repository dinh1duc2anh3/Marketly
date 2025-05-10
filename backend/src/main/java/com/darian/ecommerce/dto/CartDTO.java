package com.darian.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    // ID of the user owning the cart
    private Integer userId;

    // List of items in the cart
    private List<CartItemDTO>  items;
}
