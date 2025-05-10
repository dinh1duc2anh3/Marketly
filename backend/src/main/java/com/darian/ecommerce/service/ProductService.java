package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.enums.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    // Get product list based on role (Customer or Manager)
    <T extends ProductDTO> List<T> getProductList(UserRole role);

    // Get product details based on user ID, product ID, and role
    <T extends ProductDTO> T getProductDetails(Integer userId, Long productId, UserRole role);

    Optional<Product> getProductById(Long productId);

    // Search products by keyword and role
    <T extends ProductDTO> List<T> searchProducts(Integer userId, String keyword, UserRole role);

    // Advanced search with filters (keyword, price range, category)
    List<ProductDTO> findByFilters(String keyword, Float minPrice, Float maxPrice, String category);

    // Add a new product (Manager only)
    ManagerProductDTO addProduct(Integer userId,ProductDTO productDTO);

    // Update an existing product (Manager only)
    ManagerProductDTO updateProduct(Integer userId,Long productId, ProductDTO productDTO);

    // Delete a product by ID (Manager only)
    void deleteProduct(Long productId, Integer userId);

    // Validate product details before adding/updating
    Boolean validateProductDetails(ProductDTO productDTO);

    // Check the stock quantity of a product
    Integer checkProductQuantity(Long productId);

    // Validate conditions before deleting a product
    Boolean validateDeletion(Long productId, Integer userId);

    // Check if the user has exceeded the deletion limit
    Boolean checkDeleteLimit(Integer userId);

    // Check constraints that might prevent deletion
    Boolean checkDeletionConstraints(Long productId);

    // Check if deleting a product affects any orders
    Boolean checkOrdersAffected(Long productId);




}
