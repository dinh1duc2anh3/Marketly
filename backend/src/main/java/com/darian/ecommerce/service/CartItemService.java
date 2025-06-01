package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.CartItemDTO;
import com.darian.ecommerce.entity.Cart;
import com.darian.ecommerce.entity.CartItem;
import com.darian.ecommerce.entity.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemService {
    // Add a product to the user's cart
    CartItemDTO addToCart(Integer userId, Long productId, Integer quantity);

    // Update the quantity of a product in the user's cart
    CartItemDTO updateQuantity(Integer userId, Long productId, Integer quantity);

    @Transactional(readOnly = true)
    List<CartItem> getCartItems(Cart cart);

    CartItem getCartItem(Cart cart, Product product);

    // Remove a product from the user's cart
    CartItemDTO removeFromCart(Integer userId, Long productId);

    @Transactional
    void clearCart(Integer userId);
}
