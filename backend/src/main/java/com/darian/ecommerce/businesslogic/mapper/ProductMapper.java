package com.darian.ecommerce.businesslogic.mapper;

import com.darian.ecommerce.dto.CustomerProductDTO;
import com.darian.ecommerce.dto.RelatedProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.entity.ProductImage;
import com.darian.ecommerce.enums.AvailabilityStatus;
import com.darian.ecommerce.service.RelatedProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    private final RelatedProductService relatedProductService;

    public ProductMapper(RelatedProductService relatedProductService) {
        this.relatedProductService = relatedProductService;
    }

    // Private method to map Product entity to CustomerProductDTO
    public CustomerProductDTO mapToCustomerDTO(Product product) {
        //get images url
        List<String> images =  product.getImages().stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());

        //get availability status
        AvailabilityStatus availabilityStatus = switch (product.getStockQuantity()){
            case 0 -> AvailabilityStatus.OUT_OF_STOCK;
            default -> product.getStockQuantity() < 50
                    ? AvailabilityStatus.LOW_STOCK
                    : AvailabilityStatus.IN_STOCK;
        };

        //get related products from this product
        List<RelatedProductDTO> relatedProductDTOS = relatedProductService.suggestRelatedProducts(product.getProductId());

        return  CustomerProductDTO.builder()
                .productId(product.getProductId())
                .category(product.getCategory().getName())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .specifications(product.getSpecifications())
                .images(images)
                .availabilityStatus(availabilityStatus)
                //dang bi loi o day ne
                .relatedProducts(relatedProductDTOS)
                .build();

    }


}
