package com.darian.ecommerce.businesslogic.productdetail;

import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.Optional;

public class ManagerProductDetailFetcher implements ProductDetailFetcher{
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public ManagerProductDetailFetcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Fetch product details by product ID for Manager role, returning ManagerProductDTO
    @Override
    public <T extends ProductDTO> T fetchProductDetails(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        return (T) mapToManagerDTO(optionalProduct.get());
    }

    // Private method to map Product entity to ManagerProductDTO
    private ManagerProductDTO mapToManagerDTO(Product product) {
        ManagerProductDTO dto = new ManagerProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setBarcode("BAR-" + product.getProductId());
        return dto;
    }
}
