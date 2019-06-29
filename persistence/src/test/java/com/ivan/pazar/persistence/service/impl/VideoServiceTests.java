package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Video;
import com.ivan.pazar.persistence.model.service.VideoAddServiceModel;
import com.ivan.pazar.persistence.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VideoServiceTests {

    @Mock
    private VideoRepository videoRepository;

    private ModelMapper modelMapper;
    private VideoServiceImpl videoService;

    public VideoServiceTests() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        videoService = new VideoServiceImpl(videoRepository, modelMapper);
    }

    @Test
    public void videoService_save_expectSaved() {
        VideoAddServiceModel videoAddServiceModel = mock(VideoAddServiceModel.class);
        Video video = mock(Video.class);
        when(videoRepository.saveAndFlush(any())).thenReturn(video);
        videoService.save(videoAddServiceModel);
        verify(videoRepository).saveAndFlush(any());
    }

    @Test
    public void videoService_deleteById_expectVideoDeleted() {
        videoService.deleteById("123");
        verify(videoRepository).deleteByName("123");
    }

    @Test
    public void videoService_findById_expectVideoFound() {
        Video video = mock(Video.class);
        Optional<Video> optionalVideo = Optional.of(video);
        when(videoRepository.findById(anyString())).thenReturn(optionalVideo);
        Video byId = videoService.findById("123");
        assertEquals(video, byId);
    }

    @Test
    public void videoService_updateVideo_videoUpdated() {
        Video video = mock(Video.class);
        when(videoRepository.saveAndFlush(any())).thenReturn(video);
        videoService.updateVideo(video);
        verify(videoRepository).saveAndFlush(any());
    }
}
