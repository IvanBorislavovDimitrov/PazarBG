package com.ivan.pazar.web.config;

import com.ivan.pazar.persistence.constants.PersistenceConstants;
import com.ivan.pazar.persistence.util.Utils;
import com.ivan.pazar.web.interceptors.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    private final LogInterceptor logInterceptor;

    @Autowired
    public AdditionalResourceWebConfiguration(LogInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + PersistenceConstants.CONTENT + "/**")
                .addResourceLocations("file:" + Utils.getProfilePicturesDirectory() + "/")
                .addResourceLocations("file:" + Utils.getAdvertisementsDirectory() + "/")
                .addResourceLocations("file:" + Utils.getVideosDirectory() + "/");
    }

}
