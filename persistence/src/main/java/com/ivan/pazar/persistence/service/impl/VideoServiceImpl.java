package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Video;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.VideoAddServiceModel;
import com.ivan.pazar.persistence.model.service.VideoServiceModel;
import com.ivan.pazar.persistence.repository.VideoRepository;
import com.ivan.pazar.persistence.service.service_api.VideoServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoServiceImpl implements VideoServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;

    public VideoServiceImpl(VideoRepository videoRepository, ModelMapper modelMapper) {
        this.videoRepository = videoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VideoServiceModel save(VideoAddServiceModel videoAddServiceModel) {
        LOGGER.info(Messages.ADDING_VIDEO);
        return modelMapper.map(videoRepository.saveAndFlush(modelMapper.map(videoAddServiceModel, Video.class)), VideoServiceModel.class);
    }

    @Override
    public void deleteById(String name) {
        LOGGER.info(Messages.DELETING_VIDEO_BY_ID);
        videoRepository.deleteByName(name);
    }

    @Override
    public Video findById(String id) {
        LOGGER.info(Messages.FINDING_VIDEO_BY_ID);
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public void updateVideo(Video videoEntity) {
        LOGGER.info(Messages.UPDATING_VIDEO);
        videoRepository.saveAndFlush(videoEntity);
    }
}
