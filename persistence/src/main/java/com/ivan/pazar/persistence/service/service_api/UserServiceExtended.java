package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.service.api.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserServiceExtended extends UserService {
    User getUserByUsername(String username);
}
