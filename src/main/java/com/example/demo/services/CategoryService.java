package com.example.demo.services;

import com.example.demo.Entity.Category;
import com.example.demo.payloads.CategoryDTO;
import com.example.demo.payloads.CategoryResponse;

public interface CategoryService {

    CategoryDTO createCategory(Category category);

    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO updateCategory(Category category, Long categoryId);

    String deleteCategory(Long categoryId);
}
