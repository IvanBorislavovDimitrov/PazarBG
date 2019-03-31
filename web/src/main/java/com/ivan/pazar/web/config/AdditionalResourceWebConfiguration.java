package com.ivan.pazar.web.config;

import com.ivan.pazar.persistence.constants.PersistenceConstants;
import com.ivan.pazar.persistence.util.Utils;
import com.ivan.pazar.web.interceptors.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    @Bean
    public LogInterceptor defaultInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor())
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
