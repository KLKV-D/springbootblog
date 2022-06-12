package ru.klokov.springbootblog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klokov.springbootblog.dto.tag.TagRequest;
import ru.klokov.springbootblog.dto.tag.TagResponse;
import ru.klokov.springbootblog.entities.Tag;
import ru.klokov.springbootblog.services.TagService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/tags")
public class TagController {
    private TagService tagService;
    private ModelMapper modelMapper;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags().stream()
                .map(tag -> modelMapper.map(tag, TagResponse.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(modelMapper.map(tagService.getTagById(id), TagResponse.class));
    }

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest tagRequest) {
        Tag tag = modelMapper.map(tagRequest, Tag.class);
        return ResponseEntity.ok(modelMapper.map(tagService.createTag(tag), TagResponse.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TagResponse> updateTag(@PathVariable(name = "id") Long id,
                                                 @RequestBody TagRequest tagRequest) {
        Tag tag = modelMapper.map(tagRequest, Tag.class);
        return ResponseEntity.ok(modelMapper.map(tagService.updateTag(id, tag), TagResponse.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTagById(@PathVariable(name = "id") Long id) {
        tagService.deleteTagById(id);
        return ResponseEntity.ok("Tag with ID=" + id + " successfully deleted");
    }
}
