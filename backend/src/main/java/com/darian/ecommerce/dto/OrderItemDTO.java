package com.darian.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDTO {
    private Long productId;
    private Integer quantity;
    private Float unitPrice;
    private Float lineTotal;
}
