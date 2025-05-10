package com.darian.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String email;
    private LocalDateTime createdAt;
}

