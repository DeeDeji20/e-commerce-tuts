package com.deedeji.ecommerce.services;

import com.cloudinary.utils.ObjectUtils;
import com.deedeji.ecommerce.data.dto.request.AddProductRequest;
import com.deedeji.ecommerce.data.dto.response.AddProductResponse;
import com.deedeji.ecommerce.data.models.Category;
import com.deedeji.ecommerce.data.models.Product;
import com.deedeji.ecommerce.data.repository.ProductRepository;
import com.deedeji.ecommerce.services.cloud.CloudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ModelMapper mapper = new ModelMapper();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private final CloudService cloudService;

    private final ProductRepository productRepository;
    @Override
    public AddProductResponse addProduct(AddProductRequest addProductRequest) throws IOException {
        Product product = mapper.map(addProductRequest, Product.class);
        product.getCategories().add(
                Category.valueOf(addProductRequest.getProductCategory().toUpperCase()));
        var imgUrl =
                cloudService.upload(addProductRequest.getImage()
                        .getBytes(), ObjectUtils.emptyMap());
        log.info("Cloudinary image url {}", imgUrl);
        product.setImageUrl(imgUrl);
        Product savedProduct = productRepository.save(product);
        return buildAddProductResponse(savedProduct);
    }

    private AddProductResponse buildAddProductResponse(Product product) {
        return AddProductResponse.builder()
                .productId(product.getId())
                .code(201)
                .message("Product added successfully")
                .build();
    }
}
