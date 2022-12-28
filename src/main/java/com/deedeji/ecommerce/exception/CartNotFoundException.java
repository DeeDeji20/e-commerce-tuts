package com.deedeji.ecommerce.exception;

public class CartNotFoundException extends EcommerceExpressException {
    public CartNotFoundException(String msg) {
        super(msg);
    }
}
