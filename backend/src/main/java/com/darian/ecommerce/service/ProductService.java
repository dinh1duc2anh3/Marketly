package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.CategoryDTO;
import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.dto.ProductReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    // Get product list based on role (Customer or Manager)
    <T extends ProductDTO> List<T> getProductList(String role);

    // Get product details based on user ID, product ID, and role
    <T extends ProductDTO> T getProductDetails(Integer userId, Long productId, String role);

    // Search products by keyword and role
    <T extends ProductDTO> List<T> searchProducts(Integer userId, String keyword, String role);

    // Advanced search with filters (keyword, price range, category)
    List<ProductDTO> findByFilters(String keyword, Float minPrice, Float maxPrice, String category);

    // Suggest related products based on a product ID
    List<ProductDTO> suggestRelatedProducts(Long productId);

    // Add a new product (Manager only)
    ManagerProductDTO addProduct(ProductDTO productDTO);

    // Update an existing product (Manager only)
    ManagerProductDTO updateProduct(Long productId, ProductDTO productDTO);

    // Delete a product by ID (Manager only)
    void deleteProduct(Long productId, Integer userId);

    // Validate product details before adding/updating
    Boolean validateProductDetails(ProductDTO productDTO);

    // Check the stock quantity of a product
    Integer checkProductQuantity(Long productId);

    // Validate conditions before deleting a product
    void validateDeletion(Long productId, Integer userId);

    // Check if the user has exceeded the deletion limit
    Boolean checkDeleteLimit(Integer userId);

    // Check constraints that might prevent deletion
    Boolean checkDeletionConstraints(Long productId);

    // Check if deleting a product affects any orders
    Boolean checkOrdersAffected(Long productId);

    // Get all product categories
    List<CategoryDTO> findAllCategories();

    // Save a new category
    CategoryDTO saveCategory(CategoryDTO category);

    // Add a review for a product
    ProductReviewDTO addReview(Long productId, ProductReviewDTO reviewDTO);

    // Get all reviews for a product
    List<ProductReviewDTO> getReviews(Long productId);
}
