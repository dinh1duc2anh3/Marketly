package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    // Find cart by user ID
    Optional<Cart> findByUserId(String userId);

    // Save a cart
    Cart save(Cart cart);
}
