package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.model.service.RoleServiceModel;
import com.ivan.pazar.persistence.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    private ModelMapper modelMapper;
    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        roleService = new RoleServiceImpl(roleRepository, modelMapper);
    }

    @Test
    public void roleService_initRoles_rolesAreInitialized() {
        roleService.initRoles();
        verify(roleRepository, times(4)).save(any());
    }

    @Test
    public void roleService_getUserByUserRole_roleReturned() {
        Role role = mock(Role.class);
        when(roleRepository.getByUserRole(UserRole.ROLE_USER)).thenReturn(role);
        assertNotNull(roleService.getByUserRole(UserRole.ROLE_USER));
    }

    @Test
    public void roleService_save_roleSaved() {
        RoleServiceModel roleServiceModel = mock(RoleServiceModel.class);
        when(roleRepository.save(any())).thenReturn(mock(Role.class));
        roleService.save(roleServiceModel);
        verify(roleRepository).save(any());
    }
}
