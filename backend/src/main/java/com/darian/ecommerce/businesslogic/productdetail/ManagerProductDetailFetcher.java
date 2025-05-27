package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.businesslogic.mapper.productmapper.ProductMapper;
import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.Optional;

public class ManagerProductDetailFetcher implements ProductDetailFetcher<ManagerProductDTO>{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Constructor injection for ProductRepository
    public ManagerProductDetailFetcher(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Fetch product details by product ID for Manager role, returning ManagerProductDTO
    @Override
    public ManagerProductDTO fetchProductDetails(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        return productMapper.toManagerDTO(optionalProduct.get());
    }
}
