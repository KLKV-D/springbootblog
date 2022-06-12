package ru.klokov.springbootblog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.klokov.springbootblog.dto.category.CategoryRequest;
import ru.klokov.springbootblog.dto.category.CategoryResponse;
import ru.klokov.springbootblog.entities.Category;
import ru.klokov.springbootblog.services.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private ModelMapper modelMapper;
    private Category category;
    private ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Music", null);
    }

    @Test
    @DisplayName("Should return response entity with all categories DTOs")
    void shouldReturnResponseEntityWithAllCategoriesDtos() throws Exception {
        List<Category> categories = Arrays.asList(
                category,
                Category.builder().id(2L).name("Sport").posts(null).build(),
                Category.builder().id(3L).name("IT").posts(null).build()
        );

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Music")))
                .andExpect(jsonPath("$[1].name", is("Sport")))
                .andExpect(jsonPath("$[2].name", is("IT")));
    }

    @Test
    @DisplayName("Should return category by id")
    void shouldReturnCategoryById() throws Exception {
        long id = 1L;
        when(categoryService.getCategoryById(id)).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Music")));
    }

    @Test
    @DisplayName("Should return created category response")
    void shouldReturnCreatedCategoryResponse() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Music");
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(1L);
        categoryResponse.setName("Music");

        when(categoryService.createCategory(ArgumentMatchers.any())).thenReturn(category);
        when(modelMapper.map(category, CategoryResponse.class)).thenReturn(categoryResponse);

        String requestBody = objectWriter.writeValueAsString(categoryRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                        .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategoryById() {
    }
}