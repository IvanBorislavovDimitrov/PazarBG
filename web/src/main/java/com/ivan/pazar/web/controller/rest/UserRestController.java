package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.model.view.rest.UserRestViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public List<UserRestViewModel> allUsers(@RequestParam("username") String username) {
        return userService.findAllByUsernameContaining(username).stream()
                .map(userServiceModel -> modelMapper.map(userServiceModel, UserRestViewModel.class))
                .collect(Collectors.toList());
    }
}
