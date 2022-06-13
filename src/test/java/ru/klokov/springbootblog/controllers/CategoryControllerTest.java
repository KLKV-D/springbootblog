package ru.klokov.springbootblog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.eq;
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
    void shouldReturnResponseEntityWithAllCategoriesDTOs() throws Exception {
        Category category2 = Category.builder().id(2L).name("Sport").posts(null).build();
        Category category3 = Category.builder().id(3L).name("IT").posts(null).build();

        CategoryResponse response1 = CategoryResponse.builder().id(1L).name("Music").build();
        CategoryResponse response2 = CategoryResponse.builder().id(2L).name("Sport").build();
        CategoryResponse response3 = CategoryResponse.builder().id(3L).name("IT").build();

        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category, category2, category3));

        when(modelMapper.map(category, CategoryResponse.class)).thenReturn(response1);
        when(modelMapper.map(category2, CategoryResponse.class)).thenReturn(response2);
        when(modelMapper.map(category3, CategoryResponse.class)).thenReturn(response3);

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
        CategoryResponse response = CategoryResponse.builder().id(1L).name("Music").build();

        when(categoryService.getCategoryById(id)).thenReturn(category);
        when(modelMapper.map(category, CategoryResponse.class)).thenReturn(response);

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
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Music")));
    }

    @Test
    void shouldReturnUpdatedCategoryResponse() throws Exception {
        long id = 1L;
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Music");
        Category updatedCategory = new Category(1L, "Sport", null);
        CategoryResponse updatedCategoryResponse = new CategoryResponse();
        updatedCategoryResponse.setId(1L);
        updatedCategoryResponse.setName("Sport");

        when(categoryService.updateCategory(id, eq(ArgumentMatchers.any()))).thenReturn(updatedCategory);
        when(modelMapper.map(updatedCategory, CategoryResponse.class)).thenReturn(updatedCategoryResponse);

        String requestBody = objectWriter.writeValueAsString(categoryRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Sport")));
    }

    @Test
    void shouldReturnString() throws Exception {
        long id = category.getId();
        when(categoryService.getCategoryById(id)).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsString("successfully deleted")));
    }
}