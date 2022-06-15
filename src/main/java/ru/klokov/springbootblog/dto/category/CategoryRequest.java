package ru.klokov.springbootblog.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "Name must be not blank")
    private String name;
}
