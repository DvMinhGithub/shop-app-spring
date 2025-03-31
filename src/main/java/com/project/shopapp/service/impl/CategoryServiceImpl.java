package com.project.shopapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shopapp.model.dto.request.CategoryRequest;
import com.project.shopapp.model.entity.Category;
import com.project.shopapp.repository.CategoryRepository;
import com.project.shopapp.service.CategoryService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    MessageUtils messageUtils;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(messageUtils.getMessage(MessageKeys.CATEGORY_NOT_FOUND)));
    }

    @Override
    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException(messageUtils.getMessage(MessageKeys.CATEGORY_ALREADY_EXISTS));
        }
        Category category = Category.builder().name(request.getName()).build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest request) {
        Category existingCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(messageUtils.getMessage(MessageKeys.CATEGORY_NOT_FOUND)));

        existingCategory.setName(request.getName());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(messageUtils.getMessage(MessageKeys.CATEGORY_NOT_FOUND)));
        categoryRepository.delete(category);
    }
}
