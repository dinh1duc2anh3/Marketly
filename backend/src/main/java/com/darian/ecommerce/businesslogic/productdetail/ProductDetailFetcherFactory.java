package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.repository.ProductRepository;

public class ProductDetailFetcherFactory {
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public ProductDetailFetcherFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get the appropriate ProductDetailFetcher based on role
    public ProductDetailFetcher getFetcher(String role) {
        if ("CUSTOMER".equalsIgnoreCase(role)) {
            return new CustomerProductDetailFetcher(productRepository);
        } else if ("MANAGER".equalsIgnoreCase(role)) {
            return new ManagerProductDetailFetcher(productRepository);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
