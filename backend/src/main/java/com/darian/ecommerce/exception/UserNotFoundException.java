package com.darian.ecommerce.exception;

import com.darian.ecommerce.enums.ErrorCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
} 