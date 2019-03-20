package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.service.api.UserService;

public interface UserServiceExtended extends UserService {
    User getUserByUsername(String username);
}