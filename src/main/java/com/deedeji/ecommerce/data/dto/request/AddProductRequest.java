package com.deedeji.ecommerce.data.dto.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddProductRequest {
    private String name;
    private BigDecimal price;
    private String productCategory;
    private int quantity;
    @NotNull
    private MultipartFile image;
}
