package com.darian.ecommerce.businesslogic.productsearch;

import com.darian.ecommerce.dto.CustomerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerProductSearchFetcher implements ProductSearchFetcher{
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public CustomerProductSearchFetcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Search products by keyword for Customer role, returning CustomerProductDTO
    @Override
    public <T extends ProductDTO> List<T> searchProducts(String keyword) {
        List<Product> products = productRepository.findByKeyword(keyword);
        return (List<T>) products.stream()
                .map(this::mapToCustomerDTO)
                .collect(Collectors.toList());
    }


    // Private method to map Product entity to CustomerProductDTO
    private CustomerProductDTO mapToCustomerDTO(Product product) {
        CustomerProductDTO dto = new CustomerProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setAvailability(product.getStockQuantity() > 0 ? "In Stock" : "Out of Stock");
        List<String> stringList = new ArrayList<>();
        stringList.add("default-image1.jpg");
        stringList.add("default-image2.jpg");
        dto.setImages(stringList);
        return dto;
    }

}
