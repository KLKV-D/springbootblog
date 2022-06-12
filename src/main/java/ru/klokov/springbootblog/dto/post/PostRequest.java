package ru.klokov.springbootblog.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String text;
    private Long categoryId;
    private Set<Long> tagIds;
}
