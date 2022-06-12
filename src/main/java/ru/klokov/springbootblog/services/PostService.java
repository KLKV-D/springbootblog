package ru.klokov.springbootblog.services;

import ru.klokov.springbootblog.entities.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Long id, Post newPost);

    void deleteById(Long id);
}
