package com.darian.ecommerce.businesslogic.mapper.usermapper;

import com.darian.ecommerce.dto.UserDTO;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public  UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public  User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                // Nếu userDTO.getRole() là String (ví dụ: "ADMIN"), thì cần convert về Enum
                .role(dto.getRole() != null ? UserRole.valueOf(dto.getRole()) : null)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}