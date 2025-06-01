package com.darian.ecommerce.businesslogic.mapper.usermapper;

import com.darian.ecommerce.dto.UserDTO;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.enums.UserRole;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
//                .role(dto.getRole() != null ? UserRole.valueOf(dto.getRole().toUpperCase()) : UserRole.CUSTOMER)
                .role(UserRole.CUSTOMER)
                .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
//                .password(user.getPassword()) TODO: no include password into dto for security
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .build();
    }


}