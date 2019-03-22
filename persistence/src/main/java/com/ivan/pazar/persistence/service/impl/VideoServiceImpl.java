package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Video;
import com.ivan.pazar.persistence.model.service.VideoAddServiceModel;
import com.ivan.pazar.persistence.model.service.VideoServiceModel;
import com.ivan.pazar.persistence.repository.VideoRepository;
import com.ivan.pazar.persistence.service.service_api.VideoServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoServiceImpl implements VideoServiceExtended {

    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;

    public VideoServiceImpl(VideoRepository videoRepository, ModelMapper modelMapper) {
        this.videoRepository = videoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoServiceModel save(VideoAddServiceModel videoAddServiceModel) {
        return modelMapper.map(videoRepository.save(modelMapper.map(videoAddServiceModel, Video.class)), VideoServiceModel.class);
    }

    @Override
    public Video findById(String id) {
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public void updateVideo(Video videoEntity) {
        videoRepository.saveAndFlush(videoEntity);
    }
}
