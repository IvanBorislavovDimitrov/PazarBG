package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.VideoServiceModel;
import com.ivan.pazar.repository.VideoRepository;
import com.ivan.pazar.service.api.VideoService;
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
