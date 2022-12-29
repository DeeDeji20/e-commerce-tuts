package com.deedeji.ecommerce.data.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal price=BigDecimal.ZERO;
    private int quantity;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Category> categories =new ArrayList<>();
    private String imageUrl;
    private boolean isActive = true;
}
