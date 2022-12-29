package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.AddProductRequest;
import com.deedeji.ecommerce.data.dto.request.GetAllItemsRequest;
import com.deedeji.ecommerce.data.dto.response.AddProductResponse;
import com.deedeji.ecommerce.data.dto.response.UpdateProductResponse;
import com.deedeji.ecommerce.data.models.Product;
import com.deedeji.ecommerce.exception.ProductException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
                .get("C:\\Users\\CROWN_STAFF.DESKTOP-R8GJQ3F\\Pictures\\swit-c9.jpeg");

        MultipartFile file =
                new MockMultipartFile("swit",
                        Files.readAllBytes(path));
        addProductRequest = buildAddProductRequest(file);
    }

    private AddProductRequest buildAddProductRequest(MultipartFile file) {
        return AddProductRequest.builder()
                .name("Random")
                .productCategory("Fashion")
                .price(BigDecimal.valueOf(100.00))
                .quantity(1)
                .image(file)
                .build();
    }

    @Test
    void addProductTest() throws IOException {
        var response = productService.addProduct(addProductRequest);
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isGreaterThan(0L);
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getCode()).isEqualTo(201);
    }

    @Test
    void updateProductDetailsTest() throws ProductException {
        ObjectMapper mapper = new ObjectMapper();
        UpdateProductResponse updateProductResponse = null;

        try {
            JsonNode value = mapper.readTree("50");
            JsonPatch patch =
                    new JsonPatch(List.of(new ReplaceOperation(
                            new JsonPointer("/price"),
                            value
                    )));
            updateProductResponse = productService.updateProductDetails(1L, patch);
        } catch (JsonProcessingException | JsonPointerException e) {
            e.printStackTrace();
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        }
        assertThat(updateProductResponse).isNotNull();
        assertThat(updateProductResponse.getStatusCode()).isEqualTo(200);
        assertThat(productService.getProductById(1L).getPrice())
                .isEqualTo("50.00");
    }

    @Test
    void getProductByIdTest() throws ProductException {
        Product foundProduct = productService.getProductById(1L);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(1L);
    }

    @Test
    void getAllProductsTest(){
        var getItemsRequest = buildGetItemsRequest();
        Page<Product> productPage = productService.getAllProducts(getItemsRequest);
        log.info("Page content:::: {}", productPage);
        assertThat(productPage).isNotNull();
        assertThat(productPage.getTotalElements()).isGreaterThan(0);
    }

    @Test
    void deleteProductTest() throws ProductException {
        assertThat(productService.deleteProduct(3L)).isNotNull();
    }

    @Test
    void deactivateProductTest() throws ProductException {
           Product deactivatedProduct = productService.deactivateProduct(1L);
           assertThat(deactivatedProduct.isActive()).isFalse();
    }

    private GetAllItemsRequest buildGetItemsRequest() {
        return GetAllItemsRequest
                .builder()
                .numberOfItemsPerPage(8)
                .pageNumber(1)
                .build();
    }

}
