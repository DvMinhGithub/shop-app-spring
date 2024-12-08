package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dto.request.CategoryRequest;
import com.project.shopapp.entity.Category;

public interface ICategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(CategoryRequest request);

    Category updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
