package com.darian.ecommerce.repository;

import com.darian.ecommerce.entity.ProductReview;

import java.util.List;

public interface ProductReviewRepository {
    // Save a product review (inherited from JpaRepository)
    ProductReview save(ProductReview review);

    // Find reviews by product ID
    List<ProductReview> findByProductId(String productId);
}
