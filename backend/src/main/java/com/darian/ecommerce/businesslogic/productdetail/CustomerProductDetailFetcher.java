package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.businesslogic.mapper.productmapper.ProductMapper;
import com.darian.ecommerce.product.dto.CustomerProductDTO;
import com.darian.ecommerce.product.entity.Product;
import com.darian.ecommerce.product.repository.ProductRepository;

import java.util.Optional;

public class CustomerProductDetailFetcher implements ProductDetailFetcher<CustomerProductDTO> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Constructor injection for ProductRepository
    public CustomerProductDetailFetcher(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Fetch product details by product ID for Customer role, returning CustomerProductDTO
    @Override
    public CustomerProductDTO fetchProductDetails(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        return productMapper.toCustomerDTO(optionalProduct.get());
    }

}
