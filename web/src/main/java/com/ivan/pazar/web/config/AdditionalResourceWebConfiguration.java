package com.ivan.pazar.web.config;

import com.ivan.pazar.persistence.constants.ConfigConstants;
import com.ivan.pazar.persistence.util.Utils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + ConfigConstants.CONTENT + "/**")
                .addResourceLocations("file:" + Utils.getProfilePicturesDirectory() + "/");
    }

}
