package ru.klokov.springbootblog.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.klokov.springbootblog.dto.category.CategoryResponse;
import ru.klokov.springbootblog.dto.comment.CommentResponse;
import ru.klokov.springbootblog.dto.tag.TagResponse;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String text;
    private CategoryResponse category;
    private Set<TagResponse> tags;
    private Set<CommentResponse> comments;
}
