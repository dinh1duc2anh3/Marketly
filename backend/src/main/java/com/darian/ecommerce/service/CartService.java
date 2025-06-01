package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.CartDTO;
import com.darian.ecommerce.entity.Cart;

public interface CartService {

    Cart getOrCreateCart(Integer userId);

    // View the user's cart
    CartDTO viewCart(Integer userId);

    Cart save(Cart cart);

    // Check the availability of a cart
    Boolean checkAvailability(CartDTO cartDTO);
}

