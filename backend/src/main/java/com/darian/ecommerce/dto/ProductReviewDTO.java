package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDTO {
    private Long productId;
    private Integer customerId;
    private Integer rating;
    private String comment;
    private Date createdDate;
}
