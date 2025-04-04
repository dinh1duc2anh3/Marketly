package com.darian.ecommerce.businesslogic.productlist;

import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerProductListFetcher implements ProductListFetcher {
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public ManagerProductListFetcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Fetch product list for Manager role, returning ManagerProductDTO
    @Override
    public <T extends ProductDTO> List<T> fetchProductList() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToManagerDTO)
                .collect(Collectors.toList());
    }

    // Private method to map Product entity to ManagerProductDTO
    private ManagerProductDTO mapToManagerDTO(Product product) {
        ManagerProductDTO dto = new ManagerProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setStockQuantity(product.getStockQuantity()); // Manager-specific field
        dto.setBarcode("BAR-" + product.getProductId());  // Example field for Manager
        return dto;
    }
}
