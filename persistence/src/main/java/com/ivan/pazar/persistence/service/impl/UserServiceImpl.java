package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserRegisterServiceModel;
import com.ivan.pazar.persistence.exceptions.PasswordsMismatchException;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.repository.RoleRepository;
import com.ivan.pazar.persistence.repository.TownRepository;
import com.ivan.pazar.persistence.repository.UserRepository;
import com.ivan.pazar.persistence.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final RegionRepository regionRepository;
    private final TownRepository townRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, RegionRepository regionRepository, TownRepository townRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.regionRepository = regionRepository;
        this.townRepository = townRepository;
    }

    @Override
    public UserServiceModel save(UserRegisterServiceModel userRegisterServiceModel) {
        checkUserServiceModelValid(userRegisterServiceModel);
        User user = modelMapper.map(userRegisterServiceModel, User.class);
        try {
            user.setProfilePicture(userRegisterServiceModel.getProfilePicture().getBytes());
        } catch (IOException e) {
            throw new IllegalStateException();
        }

        if (userRepository.count() == 0) {
            user.getRoles().add(roleRepository.getByUserRole(UserRole.ROLE_ADMIN));
        } else {
            user.getRoles().add(roleRepository.getByUserRole(UserRole.ROLE_USER));
        }
        user.setRegion(regionRepository.findByName(userRegisterServiceModel.getRegion()));
        user.setTown(townRepository.findByName(userRegisterServiceModel.getTown()));

        userRepository.save(user);

        return modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public boolean isEmailFree(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUsernameFree(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public boolean isPhoneNumberFree(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }

    private void checkUserServiceModelValid(UserRegisterServiceModel userRegisterServiceModel) {
        if (!userRegisterServiceModel.getPassword().equals(userRegisterServiceModel.getConfirmPassword())) {
            throw new PasswordsMismatchException();
        }
    }
}
