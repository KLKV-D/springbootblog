package ru.klokov.springbootblog.services.impl;

import org.springframework.stereotype.Service;
import ru.klokov.springbootblog.entities.Category;
import ru.klokov.springbootblog.exceptions.ResourceAlreadyExistsException;
import ru.klokov.springbootblog.exceptions.ResourceNotFoundException;
import ru.klokov.springbootblog.repositories.CategoryRepository;
import ru.klokov.springbootblog.services.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID = " + id));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name \"" + name + "\""));
    }

    @Override
    public Category createCategory(Category category) throws ResourceAlreadyExistsException {
        String name = category.getName();
        if (categoryRepository.findByName(name).isPresent()) {
            throw new ResourceAlreadyExistsException("Category with name \"" + name + "\" already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category newCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID=" + id));
        category.setName(newCategory.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID=" + id));
        categoryRepository.deleteById(id);
    }
}
