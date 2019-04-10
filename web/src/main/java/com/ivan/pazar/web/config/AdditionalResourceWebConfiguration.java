package com.ivan.pazar.web.config;

import com.ivan.pazar.persistence.constants.PersistenceConstants;
import com.ivan.pazar.persistence.util.Utils;
import com.ivan.pazar.web.interceptors.CheckForApplicationJsonContentType;
import com.ivan.pazar.web.interceptors.CheckForLoggedUserAndRedirect;
import com.ivan.pazar.web.interceptors.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    private static final String ALL_ROUTES = "/**";
    private static final String FILE = "file:";
    private static final String SLASH = "/";
    private static final String API_ROUTES = "/api/**";
    private static final String INDEX  ="/";

    private final LogInterceptor logInterceptor;
    private final CheckForApplicationJsonContentType checkForApplicationJsonContentType;
    private final CheckForLoggedUserAndRedirect checkForLoggedUserAndRedirect;

    @Autowired
    public AdditionalResourceWebConfiguration(LogInterceptor logInterceptor, CheckForApplicationJsonContentType checkForApplicationJsonContentType, CheckForLoggedUserAndRedirect checkForLoggedUserAndRedirect) {
        this.logInterceptor = logInterceptor;
        this.checkForApplicationJsonContentType = checkForApplicationJsonContentType;
        this.checkForLoggedUserAndRedirect = checkForLoggedUserAndRedirect;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns(ALL_ROUTES);
        registry.addInterceptor(checkForApplicationJsonContentType)
                .addPathPatterns(API_ROUTES);
        registry.addInterceptor(checkForLoggedUserAndRedirect)
                .addPathPatterns(INDEX);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SLASH + PersistenceConstants.CONTENT + ALL_ROUTES)
                .addResourceLocations(FILE + Utils.getProfilePicturesDirectory() + SLASH)
                .addResourceLocations(FILE + Utils.getAdvertisementsDirectory() + SLASH)
                .addResourceLocations(FILE + Utils.getVideosDirectory() + SLASH);
    }

}
