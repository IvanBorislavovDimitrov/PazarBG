package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.UserServiceModel;
import com.ivan.pazar.repository.UserRepository;
import com.ivan.pazar.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel save(UserServiceModel userServiceModel) {
        return null;
    }
}
