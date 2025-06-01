package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.mapper.usermapper.UserMapper;
import com.darian.ecommerce.dto.LoginDTO;
import com.darian.ecommerce.dto.UserDTO;
import com.darian.ecommerce.entity.User;
import com.darian.ecommerce.enums.ErrorCode;
import com.darian.ecommerce.exception.BaseException;
import com.darian.ecommerce.exception.UserNotFoundException;
import com.darian.ecommerce.repository.UserRepository;
import com.darian.ecommerce.service.UserService;
import com.darian.ecommerce.subsystem.vnpay.VNPayResponseHandler;
import com.darian.ecommerce.utils.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, 
                         UserMapper userMapper,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        // Validate info
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // Encode password
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Convert to entity and save
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getUsername());

        // Return DTO with saved data
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) {
        // Find user by username
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + loginDTO.getUsername()));

        // Check password
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BaseException(ErrorCode.INVALID_CREDENTIALS);
        }

        log.info("User logged in successfully: {}", user.getUsername());
        // Return user data
        return userMapper.toDTO(user);
    }

    @Override
    public Boolean existedByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existedByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                // .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
                .orElseThrow(() -> new UserNotFoundException(String.format(ErrorMessages.VALIDATION_FAILED, "User not found")));
    }
}