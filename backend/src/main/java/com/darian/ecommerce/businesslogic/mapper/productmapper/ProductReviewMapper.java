package com.darian.ecommerce.businesslogic.mapper.productmapper;

import com.darian.ecommerce.dto.ProductReviewDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.entity.ProductReview;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.service.ProductService;
import com.darian.ecommerce.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class ProductReviewMapper {
    private final ProductService productService;
    private final UserService userService;

    public ProductReviewMapper(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public ProductReview toEntity(ProductReviewDTO reviewDTO) {
        Product product = productService.getProductById(reviewDTO.getProductId());

        User user = userService.getUserById(reviewDTO.getCustomerId());

        return ProductReview.builder()
                .product(product)
                .user(user)
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .build();
    }

    public ProductReviewDTO toDTO(ProductReview review) {
        return ProductReviewDTO.builder()
                .productId(review.getProduct().getProductId())
                .customerId(review.getUser().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
