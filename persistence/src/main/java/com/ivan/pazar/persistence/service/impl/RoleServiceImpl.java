package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.dto.service.RoleServiceModel;
import com.ivan.pazar.persistence.repository.RoleRepository;
import com.ivan.pazar.persistence.service.api.RoleService;
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
