package ru.klokov.springbootblog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.klokov.springbootblog.entities.Post;
import ru.klokov.springbootblog.exceptions.ResourceNotFoundException;
import ru.klokov.springbootblog.repositories.PostRepository;
import ru.klokov.springbootblog.services.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID=" + id));
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post newPost) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID=" + id));
        post.setTitle(newPost.getTitle());
        post.setText(newPost.getText());
        post.setCategory(newPost.getCategory());
        post.setTags(newPost.getTags());
        post.setComments(newPost.getComments());
        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID=" + id));
        postRepository.deleteById(id);
    }
}
