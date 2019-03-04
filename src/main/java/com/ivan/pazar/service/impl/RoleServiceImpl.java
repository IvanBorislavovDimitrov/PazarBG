package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.RoleServiceModel;
import com.ivan.pazar.repository.RoleRepository;
import com.ivan.pazar.service.api.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleServiceModel save(RoleServiceModel roleServiceModel) {
        return null;
    }
}
