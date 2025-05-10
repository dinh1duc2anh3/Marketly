package com.darian.ecommerce.entity;

import com.darian.ecommerce.enums.UserRole;
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
@Table(name = "user_account")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,
            unique = true,
            length = 30)
    private String username;

    @Column(nullable = false,
            length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false,
            unique = true,
            length = 100)
    private String email;

    @Column(name= "create_at",
            nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
