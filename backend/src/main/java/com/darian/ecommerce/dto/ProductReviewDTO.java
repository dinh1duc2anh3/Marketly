package com.darian.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDTO {
    private Long productId;
    private Integer customerId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdDate;
}
