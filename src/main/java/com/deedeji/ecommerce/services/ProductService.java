package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.AddProductRequest;
import com.deedeji.ecommerce.data.dto.response.AddProductResponse;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest addProductRequest);
}
