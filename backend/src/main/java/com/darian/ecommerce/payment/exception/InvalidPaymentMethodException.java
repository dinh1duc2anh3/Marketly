package com.darian.ecommerce.payment.exception;

import com.darian.ecommerce.config.enums.ErrorCode;
import com.darian.ecommerce.config.exception.BaseException;

public class InvalidPaymentMethodException extends BaseException {
    public InvalidPaymentMethodException(String message) {
        super( ErrorCode.INVALID_PAYMENT_METHOD, message);
    }
}
