package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CartRequest;
import com.deedeji.ecommerce.data.dto.request.GetAllItemsRequest;
import com.deedeji.ecommerce.data.dto.response.CartResponse;
import com.deedeji.ecommerce.data.models.Cart;
import com.deedeji.ecommerce.data.models.Product;
import com.deedeji.ecommerce.exception.CartNotFoundException;
import com.deedeji.ecommerce.exception.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class CartServiceImplTest {

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    private Cart savedCart;

    @BeforeEach
    void setUp(){
        Cart cart = new Cart();
        savedCart = cartService.save(cart);
    }

    @Test
    void addProductToCartTest() throws CartNotFoundException, ProductException {
        CartRequest cartRequest = CartRequest.builder()
                .cartId(savedCart.getId())
                .productId(productService
                        .getAllProducts(new GetAllItemsRequest(1, 1))
                        .getContent().get(productService
                                .getAllProducts(new GetAllItemsRequest(1, 1))
                                .getNumberOfElements()-1).getId())
                .build();
        CartResponse cartResponse = cartService.addProductToCart(cartRequest);
        log.info("response from cart {} ", cartResponse);
        assertThat(cartResponse).isNotNull();
        var cartSubTotal = cartResponse.getCart().getSubTotal();
        assertThat(cartSubTotal).isLessThan(BigDecimal.valueOf(51));
        assertThat(cartSubTotal).isGreaterThan(BigDecimal.valueOf(49.99));
    }

    @Test
    void getAllProductInCart(){
        var getItemsRequest = buildGetItemsRequest();
        Page<Cart> listOfCart =  cartService.getCartList(getItemsRequest);
        assertThat(listOfCart).isNotNull();
        assertThat(listOfCart.getTotalElements()).isGreaterThan(0);
    }

    private GetAllItemsRequest buildGetItemsRequest() {
        return GetAllItemsRequest
                .builder()
                .numberOfItemsPerPage(8)
                .pageNumber(1)
                .build();
    }

    @Test
    void removeItemFromCart() throws CartNotFoundException, ProductException {
        addProductToCartTest();
        addProductToCartTest();

    }


}
