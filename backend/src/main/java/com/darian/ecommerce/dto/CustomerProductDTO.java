package com.darian.ecommerce.dto;

import com.darian.ecommerce.entity.ProductImage;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProductDTO extends ProductDTO {
    private String availability;
    private List<String> images;  // Lưu danh sách URL thay vì kiểu String đơn lẻ
    private List<ProductDTO> relatedProducts;

    // Constructor
    public CustomerProductDTO(Long productId, String name, String description, Float price, String specifications,
                              String availability, List<ProductImage> productImages, List<ProductDTO> relatedProducts) {
        super(productId, name, description, price, specifications);
        this.availability = availability;
        this.images = productImages.stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList()); // Chuyển đổi List<ProductImage> -> List<String>
        this.relatedProducts = relatedProducts;
    }
}
