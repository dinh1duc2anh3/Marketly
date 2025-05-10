package com.darian.ecommerce.dto;

import com.darian.ecommerce.enums.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProductDTO extends ProductDTO {
    private AvailabilityStatus availabilityStatus;

    private List<RelatedProductDTO> relatedProducts;

}
