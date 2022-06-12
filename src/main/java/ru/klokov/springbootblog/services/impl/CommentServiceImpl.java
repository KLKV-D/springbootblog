package ru.klokov.springbootblog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.klokov.springbootblog.entities.Comment;
import ru.klokov.springbootblog.exceptions.ResourceNotFoundException;
import ru.klokov.springbootblog.repositories.CommentRepository;
import ru.klokov.springbootblog.services.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID=" + id));
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment newComment) {
        Comment comment =  commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID=" + id));
        comment.setText(newComment.getText());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID=" + id));
        commentRepository.deleteById(id);
    }
}
