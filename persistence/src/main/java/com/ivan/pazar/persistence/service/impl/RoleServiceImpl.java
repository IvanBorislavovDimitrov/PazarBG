package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.model.service.RoleServiceModel;
import com.ivan.pazar.persistence.repository.RoleRepository;
import com.ivan.pazar.persistence.service.api.RoleService;
import com.ivan.pazar.persistence.service.service_api.RoleServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class RoleServiceImpl implements RoleServiceExtended {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void initRoles() {
        createRolesIfNotExist();
    }

    @Override
    public RoleServiceModel save(RoleServiceModel roleServiceModel) {
        return modelMapper.map(roleRepository.save(modelMapper.map(roleServiceModel, Role.class)), RoleServiceModel.class);
    }

    private void createRolesIfNotExist() {
        if (roleRepository.count() == 0) {
            for (UserRole userRole : UserRole.values()) {
                roleRepository.save(new Role(userRole));
            }
        }
    }

    public Role getByUserRole(UserRole userRole) {
        return roleRepository.getByUserRole(userRole);
    }
}
