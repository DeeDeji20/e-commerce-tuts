package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    private Category category;
    @BeforeEach
    void setup(){
        category = new Category();
        category.setId(1L);
        category.setCategoryName("First category");
        category.setDescription("First description");
        category.setImageUrl("img url");
    }

    @Test
    void testThatCategoryCanBeAdded(){
        String response = categoryService.createCategory(category);
        assertEquals("Category created successfully", response);
    }
}