package com.ivan.pazar.web.jobs;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.constants.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteNonActiveUsersJob {

    private final UserService userService;

    @Autowired
    public DeleteNonActiveUsersJob(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRate = WebConstants.ONE_DAY)
    @Async
    public void deleteNonActiveUsers() {
        userService.deleteNonActivatedUsers();
    }
}
