package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CartRequest;
import com.deedeji.ecommerce.data.dto.request.GetAllItemsRequest;
import com.deedeji.ecommerce.data.dto.response.CartResponse;
import com.deedeji.ecommerce.data.models.Cart;
import com.deedeji.ecommerce.data.models.Item;
import com.deedeji.ecommerce.data.models.Product;
import com.deedeji.ecommerce.data.repository.CartRepository;
import com.deedeji.ecommerce.exception.CartNotFoundException;
import com.deedeji.ecommerce.exception.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;
    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public CartResponse addProductToCart(CartRequest cartRequest) throws ProductException, CartNotFoundException {
        Cart cart = cartRepository.findById(cartRequest.getCartId())
                .orElseThrow(()-> new CartNotFoundException(
                        String.format("Cart with id %d not found", cartRequest.getCartId())
                ));

        Product foundProduct = productService
                .getProductById(cartRequest.getProductId());

        Item item = buildCartItem(foundProduct);
        cart.getItems().add(item);
        Cart cartToSave = updateCartSubTotal(cart, item);
        Cart savedCart = cartRepository.save(cartToSave);

        return CartResponse.builder()
                .message("Item added to cart successfully")
                .cart(savedCart)
                .build();
    }

    @Override
    public Page<Cart> getCartList(GetAllItemsRequest getItemsRequest) {
        Pageable pageSpec = PageRequest
                .of(getItemsRequest.getPageNumber() -1,
                        getItemsRequest.getNumberOfItemsPerPage());
        return cartRepository.findAll(pageSpec);
    }

    private Cart updateCartSubTotal(Cart cart, Item item) {
        cart.setSubTotal(cart.getSubTotal()
                .add(item.getProduct().getPrice()));
        return cart;
    }

    private Item buildCartItem(Product foundProduct) {
        return Item.builder()
                .product(foundProduct)
                .quantity(1)
                .build();
    }
}
