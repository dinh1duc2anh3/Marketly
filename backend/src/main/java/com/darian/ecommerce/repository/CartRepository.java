package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.Cart;
import com.darian.ecommerce.entity.CartItem;
import com.darian.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Find cart by user ID
    Optional<Cart> findByUser_Id(Integer userId);


}
