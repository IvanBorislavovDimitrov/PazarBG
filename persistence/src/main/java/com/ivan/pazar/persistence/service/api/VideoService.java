package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.VideoAddServiceModel;
import com.ivan.pazar.persistence.model.service.VideoServiceModel;

public interface VideoService {

    VideoServiceModel save(VideoAddServiceModel videoAddServiceModel);
}
