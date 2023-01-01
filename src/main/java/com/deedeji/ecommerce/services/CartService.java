package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CartRequest;
import com.deedeji.ecommerce.data.dto.response.CartResponse;
import com.deedeji.ecommerce.data.models.Cart;
import com.deedeji.ecommerce.exception.CartNotFoundException;
import com.deedeji.ecommerce.exception.ProductException;
import org.springframework.data.domain.Page;

public interface CartService {


    Cart save(Cart cart);

    CartResponse addProductToCart(CartRequest cartRequest) throws ProductException, CartNotFoundException;

    Page<Cart> getCartList(int pageNumber);
}
