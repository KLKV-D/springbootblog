package ru.klokov.springbootblog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klokov.springbootblog.dto.post.PostRequest;
import ru.klokov.springbootblog.dto.post.PostResponse;
import ru.klokov.springbootblog.entities.Category;
import ru.klokov.springbootblog.entities.Post;
import ru.klokov.springbootblog.entities.Tag;
import ru.klokov.springbootblog.services.CategoryService;
import ru.klokov.springbootblog.services.PostService;
import ru.klokov.springbootblog.services.TagService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/posts")
public class PostController {
    private PostService postService;
    private CategoryService categoryService;
    private TagService tagService;
    private ModelMapper modelMapper;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts().stream()
                .map(post -> modelMapper.map(post, PostResponse.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(modelMapper.map(postService.getPostById(id), PostResponse.class));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        Category category = categoryService.getCategoryById(postRequest.getCategoryId());

        Set<Tag> tags = postRequest.getTagIds().stream()
                .map(tagId -> tagService.getTagById(tagId))
                .collect(Collectors.toSet());

        Post post = new Post();

        post.setTitle(postRequest.getTitle());
        post.setText(postRequest.getText());
        post.setCategory(category);
        post.setTags(tags);

        return ResponseEntity.ok(modelMapper.map(postService.createPost(post), PostResponse.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable(name = "id") Long id,
                                                   @RequestBody PostRequest postRequest) {
        Post post = postService.getPostById(id);

        Category category = categoryService.getCategoryById(postRequest.getCategoryId());

        Set<Tag> tags = postRequest.getTagIds().stream()
                .map(tagId -> tagService.getTagById(tagId))
                .collect(Collectors.toSet());

        post.setTitle(postRequest.getTitle());
        post.setText(postRequest.getText());
        post.setCategory(category);
        post.setTags(tags);

        return ResponseEntity.ok(modelMapper.map(postService.updatePost(id, post), PostResponse.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id) {
        postService.deleteById(id);
        return ResponseEntity.ok("Post with ID=" + id + " successfully deleted");
    }
}
