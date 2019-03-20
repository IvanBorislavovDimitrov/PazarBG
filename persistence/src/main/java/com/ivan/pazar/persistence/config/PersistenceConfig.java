package com.ivan.pazar.persistence.config;

import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.advertisements.DefaultAdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.user.DefaultProfilePictureManager;
import com.ivan.pazar.persistence.dao.user.ProfilePictureManager;
import com.ivan.pazar.persistence.dao.videos.DefaultVideoManager;
import com.ivan.pazar.persistence.dao.videos.VideoManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

    @Bean
    public ProfilePictureManager fileSaver() {
        return new DefaultProfilePictureManager();
    }

    @Bean
    public AdvertisementPicturesManager advertisementPicturesManager() {
        return new DefaultAdvertisementPicturesManager();
    }

    @Bean
    public VideoManager videoManager() {
        return new DefaultVideoManager();
    }
}
