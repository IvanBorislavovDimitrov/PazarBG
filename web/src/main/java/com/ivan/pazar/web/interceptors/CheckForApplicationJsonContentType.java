package com.ivan.pazar.web.interceptors;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckForApplicationJsonContentType implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString().toLowerCase()) || request.getMethod().equalsIgnoreCase(HttpMethod.PATCH.toString().toLowerCase())) {
            return request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE);
        }

        return true;
    }
}
