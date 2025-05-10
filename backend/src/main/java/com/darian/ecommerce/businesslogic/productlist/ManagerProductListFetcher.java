package com.darian.ecommerce.businesslogic.productlist;

import com.darian.ecommerce.businesslogic.mapper.ProductMapper;
import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerProductListFetcher implements ProductListFetcher<ManagerProductDTO> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Constructor injection for ProductRepository
    public ManagerProductListFetcher(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Fetch product list for Manager role, returning ManagerProductDTO
    @Override
    public List<ManagerProductDTO> fetchProductList() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::mapToManagerDTO)
                .collect(Collectors.toList());
    }

}
