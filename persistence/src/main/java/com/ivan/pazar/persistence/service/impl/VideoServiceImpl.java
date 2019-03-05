package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.dto.service.VideoServiceModel;
import com.ivan.pazar.persistence.repository.VideoRepository;
import com.ivan.pazar.persistence.service.api.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;

    public VideoServiceImpl(VideoRepository videoRepository, ModelMapper modelMapper) {
        this.videoRepository = videoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoServiceModel save(VideoServiceModel videoServiceModel) {
        return null;
    }
}
