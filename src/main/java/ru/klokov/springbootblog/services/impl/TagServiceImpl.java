package ru.klokov.springbootblog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.klokov.springbootblog.entities.Tag;
import ru.klokov.springbootblog.exceptions.ResourceNotFoundException;
import ru.klokov.springbootblog.repositories.TagRepository;
import ru.klokov.springbootblog.services.TagService;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with ID=" + id));
    }

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long id, Tag newTag) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with ID=" + id));
        tag.setName(newTag.getName());
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTagById(Long id) {
        tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found with ID=" + id));
        tagRepository.deleteById(id);
    }
}
