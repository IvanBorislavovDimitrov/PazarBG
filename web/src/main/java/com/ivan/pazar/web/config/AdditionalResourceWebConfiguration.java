package com.ivan.pazar.web.config;

import com.ivan.pazar.persistence.constants.ConfigConstants;
import com.ivan.pazar.persistence.util.Utils;
import com.ivan.pazar.web.interceptors.DefaultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    @Bean
    public DefaultInterceptor defaultInterceptor() {
        return new DefaultInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor())
                .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + ConfigConstants.CONTENT + "/**")
                .addResourceLocations("file:" + Utils.getProfilePicturesDirectory() + "/")
                .addResourceLocations("file:" + Utils.getAdvertisementsDirectory() + "/")
                .addResourceLocations("file:" + Utils.getVideosDirectory() + "/");
    }

}
