package com.deedeji.ecommerce.exception;

public class UserNotFoundException extends EcommerceExpressException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
