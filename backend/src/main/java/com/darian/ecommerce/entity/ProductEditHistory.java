package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_edit_history")
public class ProductEditHistory {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edit_history_id")
    private Long id;

    // Product being edited (1-* relationship)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "editor_user_id")
    private User editor;

    // Description of changes made
    @Column(name = "changes_made")
    private String changes; // Nội dung chỉnh sửa

    // Date of the edit
    @Column(name = "edit_date")
    private Date editDate;
}
