package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.dto.service.UserServiceModel;
import com.ivan.pazar.persistence.dto.service.register.UserRegisterServiceModel;

public interface UserService {

    UserServiceModel save(UserRegisterServiceModel userRegisterServiceModel);

    boolean isEmailFree(String email);

    boolean isUsernameFree(String username);

    boolean isPhoneNumberFree(String phoneNumber);
}
