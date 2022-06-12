package ru.klokov.springbootblog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klokov.springbootblog.dto.comment.CommentRequest;
import ru.klokov.springbootblog.dto.comment.CommentResponse;
import ru.klokov.springbootblog.entities.Comment;
import ru.klokov.springbootblog.entities.Post;
import ru.klokov.springbootblog.services.CommentService;
import ru.klokov.springbootblog.services.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/posts/{postId}/comments")
public class CommentController {
    private CommentService commentService;
    private PostService postService;
    private ModelMapper modelMapper;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(commentService.getAllComments(postId).stream()
                .map(comment -> (modelMapper.map(comment, CommentResponse.class)))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(modelMapper.map(commentService.getCommentById(id), CommentResponse.class));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@PathVariable(name = "postId") Long postId ,
                                                         @RequestBody CommentRequest commentRequest) {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        Post post = postService.getPostById(postId);
        comment.setPost(post);
        return ResponseEntity.ok(modelMapper.map(commentService.createComment(comment), CommentResponse.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable(name = "id") Long id,
                                                         @RequestBody CommentRequest commentRequest) {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        return ResponseEntity.ok(modelMapper.map(commentService.updateComment(id, comment), CommentResponse.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(name = "id") Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.ok("Comment with ID=" + id + " successfully deleted");
    }
}
