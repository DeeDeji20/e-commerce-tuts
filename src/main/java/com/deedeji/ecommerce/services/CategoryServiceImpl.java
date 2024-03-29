//package com.deedeji.ecommerce.services;
//
//import com.deedeji.ecommerce.data.models.Category;
////import com.deedeji.ecommerce.data.repository.CategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CategoryServiceImpl implements CategoryService{
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Override
//    public String createCategory(Category category) {
//        categoryRepository.save(category);
//        return "Category created successfully";
//    }
//
//    @Override
//    public Category getCategory(Long id) {
//        return categoryRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Category with such id does not exist"));
//    }
//
//    @Override
//    public List<Category> listAll() {
//        return categoryRepository.findAll();
//    }
//}
