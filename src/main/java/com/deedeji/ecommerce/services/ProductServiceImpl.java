package com.deedeji.ecommerce.services;

import com.cloudinary.utils.ObjectUtils;
import com.deedeji.ecommerce.data.dto.request.AddProductRequest;
import com.deedeji.ecommerce.data.dto.request.GetAllItemsRequest;
import com.deedeji.ecommerce.data.dto.response.AddProductResponse;
import com.deedeji.ecommerce.data.dto.response.UpdateProductResponse;
import com.deedeji.ecommerce.data.models.Category;
import com.deedeji.ecommerce.data.models.Product;
import com.deedeji.ecommerce.data.repository.ProductRepository;
import com.deedeji.ecommerce.exception.ProductException;
import com.deedeji.ecommerce.services.cloud.CloudService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public UpdateProductResponse updateProductDetails(long productId, JsonPatch patch) throws ProductException, JsonPatchException {
        Product foundProduct =
                productRepository.findById(productId)
                        .orElseThrow(()-> new ProductException(
                                String.format("product with id %d not found",
                                        productId)
                        ));
        Product updatedProduct = applyPatchToProduct(patch, foundProduct);
        if (updatedProduct==null) throw new JsonPatchException("update failed");
        Product savedProduct=productRepository.save(updatedProduct);
        return buildUpdateResponse(savedProduct);
    }

    private UpdateProductResponse buildUpdateResponse(Product savedProduct) {
        return UpdateProductResponse.builder()
                .productName(savedProduct.getName())
                .price(savedProduct.getPrice())
                .message("update successful")
                .statusCode(200)
                .build();
    }

    private Product applyPatchToProduct(JsonPatch patch, Product foundProduct) {
        var productNode = objectMapper.convertValue(foundProduct, JsonNode.class);

        try {
            JsonNode patchedProductNode =  patch.apply(productNode);
            Product updatedProduct =
                    objectMapper.readValue(objectMapper.writeValueAsBytes(patchedProductNode),
                            Product.class);
            return updatedProduct;
        } catch (JsonPatchException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public  Product getProductById(long id) throws ProductException {
        return productRepository.findById(id).orElseThrow(()-> new ProductException(String.format("Product with Id %d not found", id)));
    }

    @Override
    public Page<Product> getAllProducts(GetAllItemsRequest getItemsRequest) {
        Pageable pageSpecs = PageRequest
                .of(getItemsRequest.getPageNumber() -1,
                        getItemsRequest.getNumberOfItemsPerPage());
        return productRepository.findAll(pageSpecs);
    }

    @Override
    public String deleteProduct(long id) {
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }

    @Override
    public Product deactivateProduct(long id) throws ProductException {
        Product foundProduct = productRepository.findById(id).orElseThrow(()-> new ProductException(String.format("Product not %d found", id)));
        foundProduct.setActive(false);
        return productRepository.save(foundProduct);
    }

    private AddProductResponse buildAddProductResponse(Product product) {
        return AddProductResponse.builder()
                .productId(product.getId())
                .code(201)
                .message("Product added successfully")
                .build();
    }
}
