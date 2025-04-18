package com.darian.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "category")
public class Category {
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the category
    @Column(name = "category_name", nullable = false)
    private String name;

    // Description of the category
    @Column(name = "category_description")
    private String description;
}
