package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.RoleServiceModel;
import com.ivan.pazar.persistence.repository.RoleRepository;
import com.ivan.pazar.persistence.service.service_api.RoleServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class RoleServiceImpl implements RoleServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void initRoles() {
        LOGGER.info(Messages.INITIALIZING_ROLES);
        createRolesIfNotExist();
    }

    @Override
    public RoleServiceModel save(RoleServiceModel roleServiceModel) {
        LOGGER.info(Messages.ADDING_ROLE);
        return modelMapper.map(roleRepository.save(modelMapper.map(roleServiceModel, Role.class)), RoleServiceModel.class);
    }

    private void createRolesIfNotExist() {
        if (roleRepository.count() == 0) {
            LOGGER.info(Messages.CREATING_ROLES);
            for (UserRole userRole : UserRole.values()) {
                roleRepository.save(new Role(userRole));
            }
        }
    }

    public Role getByUserRole(UserRole userRole) {
        LOGGER.info(Messages.GETTING_BY_USER_ROLE);
        return roleRepository.getByUserRole(userRole);
    }
}
