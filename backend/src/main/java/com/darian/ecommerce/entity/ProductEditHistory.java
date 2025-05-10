package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_edit_history")
public class ProductEditHistory {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edit_id")
    private Long id;

    // Product being edited (1-* relationship)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "editor_user_id")
    private User editor;

    // Date of the edit
    @Column(name = "edit_timestamp")
    private LocalDateTime editDate;

    // Description of changes made
    @Column(name = "changes_made")
    private String changes; // Nội dung chỉnh sửa


}
