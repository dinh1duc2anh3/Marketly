package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductReview {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ID of the product being reviewed
    private String productId;

    // ID of the customer who wrote the review
    private String customerId;

    // Rating given by the customer (e.g., 1-5)
    private int rating;

    // Comment or review content
    private String comment;

    // Date and time the review was created
    private Date createdDate;
}
