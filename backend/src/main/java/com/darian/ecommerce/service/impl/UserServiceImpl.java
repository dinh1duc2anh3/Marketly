package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.dto.UserDTO;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.repository.UserRepository;
import com.darian.ecommerce.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Register a new user
    @Override
    public UserDTO register(UserDTO userDTO) {
        //validate info
        // check if username, email existed
        if (userRepository.existsByUsername(userDTO.getUsername())) throw new RuntimeException("Username already exists.");
        if (userRepository.existsByEmail(userDTO.getEmail())) throw new RuntimeException("Email already exists.");

        User user = mapToUserEntity(userDTO);
        // Save to DB
        User savedUser = userRepository.save(user);

        // Return UserDTO with the saved data
        return mapToUserDTO(savedUser);
    }

    @Override
    public Boolean existedByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existedByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //     Load user by username for authentication
    @Override
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return mapToUserDTO(user);
    }

    @Override
    public User getUserById(Integer customerId) {
        return userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole().toString());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    private User mapToUserEntity(UserDTO userDTO) {
        User user = new User();

        // Set ID nếu cần thiết (tùy use-case như update)
        user.setId(userDTO.getId());

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        // Nếu userDTO.getRole() là String (ví dụ: "ADMIN"), thì cần convert về Enum
        if (userDTO.getRole() != null) {
            user.setRole(UserRole.valueOf(userDTO.getRole()));
        }

        user.setCreatedAt(userDTO.getCreatedAt());
        return user;
    }

}