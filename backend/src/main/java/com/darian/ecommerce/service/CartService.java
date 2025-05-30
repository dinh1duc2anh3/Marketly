package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.CartDTO;
import com.darian.ecommerce.dto.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CartService {
    // Add a product to the user's cart
    CartDTO addProductToCart(Integer userId, Long productId, Integer quantity);

    // View the user's cart
    CartDTO viewCart(Integer userId);

    // Update the quantity of a product in the user's cart
    CartDTO updateCart(Integer userId, Long productId, Integer quantity);

    // Remove a product from the user's cart
    CartDTO removeFromCart(Integer userId, Long productId);

    // Empty the user's cart
    void emptyCart(Integer userId);

    // Get the list of items in the user's cart
    List<CartItemDTO> getCartItems(Integer userId);
}

