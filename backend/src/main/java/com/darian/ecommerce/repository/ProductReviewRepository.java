package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.ProductReview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository {
    // Save a product review (inherited from JpaRepository)
    ProductReview save(ProductReview review);

    // Find reviews by product ID
    List<ProductReview> findByProductId(Long productId);
}
