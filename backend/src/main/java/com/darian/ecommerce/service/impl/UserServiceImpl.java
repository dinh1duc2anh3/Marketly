package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.mapper.usermapper.UserMapper;
import com.darian.ecommerce.config.exception.user.UsernameNotFoundException;
import com.darian.ecommerce.dto.UserDTO;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.repository.UserRepository;
import com.darian.ecommerce.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Register a new user
    @Override
    public UserDTO register(UserDTO userDTO) {
        //validate info
        // check if username, email existed
        if (userRepository.existsByUsername(userDTO.getUsername())) throw new RuntimeException("Username already exists.");
        if (userRepository.existsByEmail(userDTO.getEmail())) throw new RuntimeException("Email already exists.");

        User user = userMapper.toEntity(userDTO);
        // Save to DB
        User savedUser = userRepository.save(user);

        // Return UserDTO with the saved data
        return userMapper.toDTO(savedUser);
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

        return userMapper.toDTO(user);
    }

    @Override
    public User getUserById(Integer customerId) {
        return userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }


}