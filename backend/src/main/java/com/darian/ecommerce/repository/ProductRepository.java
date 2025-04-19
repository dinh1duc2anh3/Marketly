package com.darian.ecommerce.repository;

import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    // Find all products (inherited from JpaRepository)
    List<Product> findAll();

    // Find product by ID (inherited from JpaRepository)
    Optional<Product> findById(Long productId);

    // Find products by keyword (custom query)
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> findByKeyword(String keyword);


    // Find products by filters (custom query)
    @Query("SELECT p FROM Product p WHERE " +
            "(p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR :keyword IS NULL) " +
            "AND p.price BETWEEN :minPrice AND :maxPrice " +
            "AND (p.category.name = :category OR :category IS NULL)")
    List<ProductDTO> findByFilters(String keyword, Float minPrice, Float maxPrice, String category);

    // Find related products (custom query via RelatedProduct)
    @Query("SELECT p.relatedProduct FROM RelatedProduct p WHERE p.product.productId = :productId")
    List<Product> findRelatedProducts(Long productId);

    // Save a product (inherited from JpaRepository)
    Product save(Product product);

    // Delete a product by ID (inherited from JpaRepository)
    void deleteById(Long productId);

}
