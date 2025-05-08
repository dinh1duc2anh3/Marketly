package com.darian.ecommerce.entity;

import com.darian.ecommerce.enums.ActionType;
import com.darian.ecommerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
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
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private ActionType actionType;

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
    private UserRole role;

    // Timestamp of the action
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
