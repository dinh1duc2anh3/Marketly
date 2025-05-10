package com.darian.ecommerce.service;

import com.darian.ecommerce.dto.UserDTO;
import com.darian.ecommerce.entity.User;

public interface UserService {
    // Register a new user
    UserDTO register(UserDTO userDTO);

    // Load user by username (for authentication)
//    UserDTO loadUserByUsername(String username);

    Boolean existedByUsername(String username);

    Boolean existedByEmail(String email);

    User getUserById(Integer customerId);


}
