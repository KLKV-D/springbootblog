package ru.klokov.springbootblog.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.klokov.springbootblog.entities.Category;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void findByName() {
        Category category = new Category(1L, "Music", null);

        categoryRepository.save(category);

        Optional<Category> expected = categoryRepository.findByName("Music");

        assertThat(expected).isNotNull();
        assertThat(expected.get().getId()).isEqualTo(category.getId());
        assertThat(expected.get().getName()).isEqualTo(category.getName());
        assertThat(expected.get().getPosts()).isEqualTo(category.getPosts());
    }
}