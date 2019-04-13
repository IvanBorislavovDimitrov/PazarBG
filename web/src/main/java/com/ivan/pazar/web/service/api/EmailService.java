package com.ivan.pazar.web.service.api;

import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public interface EmailService {
    void sendNotificationForRegistering(UserRegisterBindingModel userRegisterBindingModel, String id);

    @Scheduled(cron = WebConstants.THREE_AM_EVERY_DAY)
    @Async
    void sendDailyNotifications();
}
