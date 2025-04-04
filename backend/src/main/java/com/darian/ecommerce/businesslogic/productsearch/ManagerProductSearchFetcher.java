package com.darian.ecommerce.businesslogic.productsearch;

import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.List;

public class ManagerProductSearchFetcher implements ProductSearchFetcher{
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public ManagerProductSearchFetcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Search products by keyword for Manager role, returning ManagerProductDTO
    @Override
    public <T extends ProductDTO> List<T> searchProducts(String keyword) {
        List<Product> products = productRepository.findByKeyword(keyword);
        return (List<T>) products.stream()
                .map(this::mapToManagerDTO)
                .collect(Collectors.toList());
    }

    // Private method to map Product entity to ManagerProductDTO
    private ManagerProductDTO mapToManagerDTO(Product product) {
        //hình như các mapToManagerDTO và mapToCustomerDTO đang lấy thiếu info hay sao ấy
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
