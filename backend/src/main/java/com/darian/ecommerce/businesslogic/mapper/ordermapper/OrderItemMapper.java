package com.darian.ecommerce.businesslogic.mapper.ordermapper;

import com.darian.ecommerce.dto.OrderItemDTO;
import com.darian.ecommerce.entity.OrderItem;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {

    private final ProductService productService;

    public OrderItemMapper(ProductService productService) {
        this.productService = productService;
    }


    public  OrderItemDTO toDTO(OrderItem item) {
        return OrderItemDTO.builder()
                .productId(item.getProduct().getProductId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .lineTotal(item.getLineTotal())
                .build();
    }

    public  List<OrderItemDTO> toDTOList(List<OrderItem> items) {
        return items.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OrderItem toEntity(OrderItemDTO dto) {
        Product product = productService.getProductById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return OrderItem.builder()
                .product(product)
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .build();
    }

    public List<OrderItem> toEntityList(List<OrderItemDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
