package com.ivan.pazar.web.interceptors;

import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckForLoggedUserAndRedirect implements HandlerInterceptor {

    private final UserConfiguration userConfiguration;

    @Autowired
    public CheckForLoggedUserAndRedirect(UserConfiguration userConfiguration) {
        this.userConfiguration = userConfiguration;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loggedUserUsername = userConfiguration.loggedUserUsername();
        if (request.getSession().getAttribute(WebConstants.ADVERTISED) != null) {
            return true;
        }
        request.getSession().setAttribute(WebConstants.ADVERTISED, true);
        if (WebConstants.ANONYMOUS_USER.equalsIgnoreCase(loggedUserUsername)) {
            response.sendRedirect(WebConstants.REDIRECT_TO_ADVERT_REGISTER);
        }

        return true;
    }
}
