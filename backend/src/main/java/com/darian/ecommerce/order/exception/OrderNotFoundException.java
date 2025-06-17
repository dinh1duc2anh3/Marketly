package com.darian.ecommerce.order.exception;

import com.darian.ecommerce.config.enums.ErrorCode;
import com.darian.ecommerce.config.exception.BaseException;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(String message) {
        super(ErrorCode.ORDER_NOT_FOUND, message);
    }
}
