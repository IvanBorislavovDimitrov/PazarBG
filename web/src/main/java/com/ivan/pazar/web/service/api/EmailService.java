package com.ivan.pazar.web.service.api;

import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;

public interface EmailService {
    void sendNotificationForRegistering(UserRegisterBindingModel userRegisterBindingModel, String id);

}
