package com.darian.ecommerce.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
}
