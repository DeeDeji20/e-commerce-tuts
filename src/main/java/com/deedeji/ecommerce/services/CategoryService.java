package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.models.Category;

import java.util.List;

public interface CategoryService {
    String createCategory(Category category);

    Category getCategory(Long id);
    List<Category> listAll();
}
