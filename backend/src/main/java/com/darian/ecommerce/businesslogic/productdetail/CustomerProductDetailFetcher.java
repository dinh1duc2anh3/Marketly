package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.dto.CustomerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.Optional;

public class CustomerProductDetailFetcher implements ProductDetailFetcher {
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public CustomerProductDetailFetcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Fetch product details by product ID for Customer role, returning CustomerProductDTO
    @Override
    public <T extends ProductDTO> T fetchProductDetails(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        return (T) mapToCustomerDTO(optionalProduct.get());
    }

    // Private method to map Product entity to CustomerProductDTO
    private CustomerProductDTO mapToCustomerDTO(Product product) {
        CustomerProductDTO dto = new CustomerProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setAvailability(product.getStockQuantity() > 0 ? "In Stock" : "Out of Stock");
        dto.setImages("default-image.jpg");
        return dto;
    }
}
