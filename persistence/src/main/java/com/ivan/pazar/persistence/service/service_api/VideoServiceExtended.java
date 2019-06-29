package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.Video;
import com.ivan.pazar.persistence.service.api.VideoService;

public interface VideoServiceExtended extends VideoService {
    Video findById(String id);

    void updateVideo(Video videoEntity);
}
