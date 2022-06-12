package ru.klokov.springbootblog.dto.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagResponse {
    private Long id;
    private String name;
}
