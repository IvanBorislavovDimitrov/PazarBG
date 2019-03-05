package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.domain.model.dto.service.UserServiceModel;

public interface UserService {

    UserServiceModel save(UserServiceModel userServiceModel);
}
