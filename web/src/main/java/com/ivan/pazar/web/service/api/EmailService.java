package com.ivan.pazar.web.service.api;

import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import static com.ivan.pazar.web.constants.WebConstants.ONE_DAY;

public interface EmailService {
    void sendNotificationForRegistering(UserRegisterBindingModel userRegisterBindingModel, String id);

    @Scheduled(fixedRate = ONE_DAY)
    @Async
    void sendDailyNotifications();
}
