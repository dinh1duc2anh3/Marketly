package com.darian.ecommerce.businesslogic.productlist;

import com.darian.ecommerce.dto.CustomerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerProductListFetcher implements ProductListFetcher{
    private final ProductRepository productRepository;

    // Constructor injection for ProductRepository
    public CustomerProductListFetcher(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Fetch product list for Customer role, returning CustomerProductDTO
    @Override
    public    List<CustomerProductDTO>  fetchProductList() {
        //find all hay là find 1 số sản phẩm tiêu biểu thôi ? giữa customer và manager khi find all thì có khác gì nhau ko , kiểu có sản phẩm nào mà manager thì thấy còn custoemr thì ko thấy ko ?
        List<Product> products = productRepository.findAll();
        // logic map ntn là ntn ?
        return products.stream()
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
        List<String> stringList = new ArrayList<>() ;
        stringList.add("default-image1.jpg");
        stringList.add("default-image2.jpg");
        dto.setImages(stringList);
        return dto;
    }

}
