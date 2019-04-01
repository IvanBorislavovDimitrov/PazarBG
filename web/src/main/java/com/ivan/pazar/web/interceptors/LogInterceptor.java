package com.ivan.pazar.web.interceptors;

import com.ivan.pazar.persistence.json.JsonParser;
import com.ivan.pazar.persistence.model.service.LogServiceModel;
import com.ivan.pazar.persistence.service.api.LogService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.model.view.LogViewModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserConfiguration userConfiguration;

    @Autowired
    private JsonParser jsonParser;

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

        LOGGER.info(jsonParser.toJson(logViewModel));

        logService.save(modelMapper.map(logViewModel, LogServiceModel.class));

        return true;
    }
}
