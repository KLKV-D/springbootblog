package ru.klokov.springbootblog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klokov.springbootblog.dto.category.CategoryRequest;
import ru.klokov.springbootblog.dto.category.CategoryResponse;
import ru.klokov.springbootblog.entities.Category;
import ru.klokov.springbootblog.services.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {
    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories.stream()
                .map(c -> modelMapper.map(c, CategoryResponse.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(modelMapper.map(categoryService.getCategoryById(id), CategoryResponse.class));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        return ResponseEntity.ok(modelMapper.map(categoryService.createCategory(category), CategoryResponse.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable(name = "id") Long id,
                                                           @RequestBody CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        return ResponseEntity.ok(modelMapper.map(categoryService.updateCategory(id, category), CategoryResponse.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category with ID=" + id + " successfully deleted!");
    }
}
