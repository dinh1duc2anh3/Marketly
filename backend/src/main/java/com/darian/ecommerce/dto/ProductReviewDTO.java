package com.darian.ecommerce.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDTO {
    private String productId;
    private String customerId;
    private int rating;
    private String comment;
    private Date createdDate;
}
