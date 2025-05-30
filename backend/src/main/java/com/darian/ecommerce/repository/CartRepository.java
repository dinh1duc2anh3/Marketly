package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Find cart by user ID
    Optional<Cart> findByUser_Id(Integer userId);

    // Save a cart
    Cart save(Cart cart);
}
