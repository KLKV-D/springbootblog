package ru.klokov.springbootblog.dto.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagRequest {
    private String name;
}
