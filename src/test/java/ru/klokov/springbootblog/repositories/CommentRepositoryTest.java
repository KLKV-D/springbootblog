package ru.klokov.springbootblog.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.klokov.springbootblog.entities.Comment;
import ru.klokov.springbootblog.entities.Post;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    void findAllByPostId() {
        Post post = new Post();
        postRepository.save(post);

        Comment comment1 = new Comment();
        comment1.setText("Comment 1");
        comment1.setPost(post);
        Comment comment2 = new Comment();
        comment2.setText("Comment 2");
        comment2.setPost(post);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findByPostId(1L);

        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments).isEqualTo(Arrays.asList(comment1, comment2));
    }
}