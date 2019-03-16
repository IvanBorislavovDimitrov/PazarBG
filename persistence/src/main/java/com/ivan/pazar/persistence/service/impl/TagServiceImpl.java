package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.model.service.TagServiceModel;
import com.ivan.pazar.persistence.repository.TagRepository;
import com.ivan.pazar.persistence.service.api.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagServiceModel save(TagServiceModel tagServiceModel) {
        return null;
    }
}
