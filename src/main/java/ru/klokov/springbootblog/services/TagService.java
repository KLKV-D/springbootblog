package ru.klokov.springbootblog.services;

import ru.klokov.springbootblog.entities.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();

    //PagedResponse

    Tag getTagById(Long id);

    Tag createTag(Tag tag);

    Tag updateTag(Long id, Tag newTag);

    void deleteTagById(Long id);
}
