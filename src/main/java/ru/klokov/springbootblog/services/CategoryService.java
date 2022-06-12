package ru.klokov.springbootblog.services;

import ru.klokov.springbootblog.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);
    Category createCategory(Category category);

    Category updateCategory(Long id, Category newCategory);

    void deleteCategoryById(Long id);
}
