package com.ivan.pazar.web.interceptors;

import com.ivan.pazar.persistence.model.service.LogServiceModel;
import com.ivan.pazar.persistence.service.api.LogService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.model.view.LogViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserConfiguration userConfiguration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = userConfiguration.loggedUserUsername();
        LocalDateTime date = LocalDateTime.now();
        String requestUrl = request.getRequestURI();
        LogViewModel logViewModel = new LogViewModel.Builder()
                .username(username)
                .date(date)
                .action(requestUrl)
                .build();

        logService.save(modelMapper.map(logViewModel, LogServiceModel.class));

        return true;
    }
}
