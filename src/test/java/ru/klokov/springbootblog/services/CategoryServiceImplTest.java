package ru.klokov.springbootblog.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.klokov.springbootblog.entities.Category;
import ru.klokov.springbootblog.exceptions.ResourceAlreadyExists;
import ru.klokov.springbootblog.repositories.CategoryRepository;
import ru.klokov.springbootblog.services.impl.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setup() {
        category = Category.builder()
                .id(1L)
                .name("Music")
                .posts(null)
                .build();
    }

    @Test
    @DisplayName("Should return all categories")
    void shouldReturnAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        categories.add(new Category(2L, "Sport", null));
        categories.add(new Category(3L, "IT", null));

        given(categoryRepository.findAll()).willReturn(categories);

        List<Category> expected = categoryService.getAllCategories();

        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(3);
        assertEquals(expected, categories);
    }

    @Test
    @DisplayName("Should return category by id")
    void shouldReturnCategoryById() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        Category expected = categoryService.getCategoryById(category.getId());

        assertThat(expected).isNotNull();
        assertEquals(expected, category);
    }

    @Test
    @DisplayName("Should return created category")
    void shouldReturnCreatedCategory() {
        given(categoryRepository.findByName(category.getName())).willReturn(Optional.empty());

        given(categoryRepository.save(category)).willReturn(category);

        Category createdCategory = categoryService.createCategory(category);

        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory).isEqualTo(category);
    }

    @Test
    @DisplayName("Should throw ResourceAlreadyExists exception")
    void shouldThrowResourceAlreadyExistsException() {
        given(categoryRepository.findByName(category.getName())).willReturn(Optional.of(category));

        assertThrows(ResourceAlreadyExists.class, () -> {
            categoryService.createCategory(category);
        });

        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Should return updated category")
    void shouldReturnUpdatedCategory() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(categoryRepository.save(category)).willReturn(category);
        category.setName("New Name");

        Category updatedCategory = categoryService.updateCategory(1L, category);

        assertThat(updatedCategory.getName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("Delete category test")
    void deleteCategoryTest() {
        Long categoryId = 1L;

        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        willDoNothing().given(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategoryById(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}