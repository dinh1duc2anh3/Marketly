package com.darian.ecommerce.repository;

import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(String productId);

    List<Product> findByKeyword(String keyword);

    List<Product> findAll();

    List<ProductDTO> findByFilters(String keyword, float minPrice, float maxPrice, String category);

    List<Product> findRelatedProducts(String productId);
}
