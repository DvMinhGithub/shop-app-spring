package com.project.shopapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shopapp.dto.request.CategoryRequest;
import com.project.shopapp.model.Category;
import com.project.shopapp.repository.CategoryRepository;
import com.project.shopapp.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists");
        }
        Category category = Category.builder().name(request.getName()).build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest request) {
        Category existingCategory =
                categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(request.getName());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
}
