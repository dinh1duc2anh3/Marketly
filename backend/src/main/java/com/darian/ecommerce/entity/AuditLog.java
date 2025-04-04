package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Type of action (e.g., SEARCH_PRODUCTS, ADD_PRODUCT)
    private String actionType;

    // ID of the user performing the action
    private String userId;

    // ID of the product involved (nullable)
    private String productId;

    // Keyword used in search (nullable)
    private String keyword;

    // Role of the user (e.g., CUSTOMER, MANAGER)
    private String role;

    // Timestamp of the action
    private LocalDateTime timestamp;
}
