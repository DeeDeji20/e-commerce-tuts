package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.models.Category;
import com.deedeji.ecommerce.data.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String createCategory(Category category) {
        categoryRepository.save(category);
        return "Category created successfully";
    }
}
