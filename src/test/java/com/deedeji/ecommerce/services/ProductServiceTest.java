package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.AddProductRequest;
import com.deedeji.ecommerce.data.dto.response.AddProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@Slf4j
public class ProductServiceTest {

    @Autowired
    private  ProductService productService;

    private AddProductRequest addProductRequest;

    private AddProductResponse addProductResponse;

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths
                .get("C:\\Users\\CROWN_STAFF.DESKTOP-R8GJQ3F\\Pictures\\peak.jpeg");

        MultipartFile file =
                new MockMultipartFile("peak",
                        Files.readAllBytes(path));
        addProductRequest = buildAddProductRequest(file);
    }

    private AddProductRequest buildAddProductRequest(MultipartFile file) {
        return AddProductRequest.builder()
                .name("Milk")
                .productCategory("Beverages")
                .price(BigDecimal.valueOf(300.00))
                .quantity(10)
                .image(file)
                .build();
    }

    @Test
    void addProductTest(){
        var response = productService.addProduct(addProductRequest);
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isGreaterThan(0L);
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getCode()).isEqualTo(201);
    }

}
