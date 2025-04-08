package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.CartDTO;
import com.darian.ecommerce.dto.CartItemDTO;

import java.util.List;

public interface CartService {
    // Add a product to the user's cart
    CartDTO addProductToCart(String userId, String productId, int quantity);

    // View the user's cart
    CartDTO viewCart(String userId);

    // Update the quantity of a product in the user's cart
    CartDTO updateCart(String userId, String productId, int quantity);

    // Remove a product from the user's cart
    CartDTO removeFromCart(String userId, String productId);

    // Empty the user's cart
    void emptyCart(String userId);

    // Get the user ID associated with a cart ID
    String getUserId(String cartId);

    // Get the list of items in the user's cart
    List<CartItemDTO> getCartItems(String userId);
}

