package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @Column(name = "audit_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Type of action (e.g., SEARCH_PRODUCTS, ADD_PRODUCT)
    @Column(name = "action_type")
    private String actionType;

    // ID of the user performing the action
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ID of the product involved (nullable)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Keyword used in search (nullable)
    private String keyword;

    // Role of the user (e.g., CUSTOMER, MANAGER)
    private String role;

    // Timestamp of the action
    private LocalDateTime timestamp;
}
