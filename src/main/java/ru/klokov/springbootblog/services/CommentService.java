package ru.klokov.springbootblog.services;

import ru.klokov.springbootblog.entities.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments(Long postId);

    Comment getCommentById(Long id);

    Comment createComment(Comment comment);

    Comment updateComment(Long id, Comment newComment);

    void deleteCommentById(Long id);
}
