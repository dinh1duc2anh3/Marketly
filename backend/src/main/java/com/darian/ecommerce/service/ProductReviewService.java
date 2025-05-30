package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.ProductReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductReviewService {
    // Add a review for a product
    ProductReviewDTO addReview(ProductReviewDTO reviewDTO);

    // Get all reviews for a product
    List<ProductReviewDTO> getReviews(Long productId);
}
