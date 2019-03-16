package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.model.service.VideoServiceModel;
import com.ivan.pazar.persistence.repository.VideoRepository;
import com.ivan.pazar.persistence.service.api.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
