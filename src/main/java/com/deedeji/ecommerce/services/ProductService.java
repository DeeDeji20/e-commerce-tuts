package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.AddProductRequest;
import com.deedeji.ecommerce.data.dto.request.GetAllItemsRequest;
import com.deedeji.ecommerce.data.dto.response.AddProductResponse;
import com.deedeji.ecommerce.data.dto.response.UpdateProductResponse;
import com.deedeji.ecommerce.data.models.Product;
import com.deedeji.ecommerce.exception.ProductException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest addProductRequest) throws IOException;

    UpdateProductResponse updateProductDetails(long productId, JsonPatch patch) throws ProductException, JsonPatchException;

    Product getProductById(long id) throws ProductException;

    Page<Product> getAllProducts(GetAllItemsRequest getItemsRequest);

    String deleteProduct(long id);
}
