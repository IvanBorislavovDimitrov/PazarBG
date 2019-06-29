package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.model.view.rest.LoggedUserUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/logged")
public class LoggedUserUsernameRestController {

    private final UserConfiguration userConfiguration;

    @Autowired
    public LoggedUserUsernameRestController(UserConfiguration userConfiguration) {
        this.userConfiguration = userConfiguration;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public LoggedUserUsername loggedUserUsername() {
        LoggedUserUsername loggedUserUsername = new LoggedUserUsername();
        loggedUserUsername.setUsername(userConfiguration.loggedUserUsername());

        return loggedUserUsername;
    }
}
