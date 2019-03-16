package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.dao.FileSaver;
import com.ivan.pazar.persistence.exceptions.PasswordsMismatchException;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserRegisterServiceModel;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.repository.RoleRepository;
import com.ivan.pazar.persistence.repository.TownRepository;
import com.ivan.pazar.persistence.repository.UserRepository;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.persistence.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String USER_PROFILE_PICTURE_PREFIX = "profile_picture_";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final RegionRepository regionRepository;
    private final TownRepository townRepository;
    private final FileSaver fileSaver;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, RegionRepository regionRepository, TownRepository townRepository, FileSaver fileSaver) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.regionRepository = regionRepository;
        this.townRepository = townRepository;
        this.fileSaver = fileSaver;
    }

    @Override
    public UserServiceModel save(UserRegisterServiceModel userRegisterServiceModel) {
        checkUserServiceModelValid(userRegisterServiceModel);
        User user = modelMapper.map(userRegisterServiceModel, User.class);
        String profilePictureName = getProfilePictureName(userRegisterServiceModel);
        user.setProfilePictureName(profilePictureName);
        if (profilePictureName != null) {
            user.setProfilePictureName(profilePictureName);
            savePicture(profilePictureName, userRegisterServiceModel.getProfilePicture());
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

    @Override
    public UserServiceModel findUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser.map(user -> modelMapper.map(user, UserServiceModel.class)).orElse(null);
    }

    private void savePicture(String profilePictureName, MultipartFile multipartPicture) {
        byte[] bytes;
        try {
            bytes = multipartPicture.getBytes();
            fileSaver.saveProfilePicture(profilePictureName, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUserServiceModelValid(UserRegisterServiceModel userRegisterServiceModel) {
        if (!userRegisterServiceModel.getPassword().equals(userRegisterServiceModel.getConfirmPassword())) {
            throw new PasswordsMismatchException();
        }
    }

    private String getProfilePictureName(UserRegisterServiceModel userRegisterServiceModel) {
        if (userRegisterServiceModel.getProfilePicture().isEmpty()) {
            return null;
        }
        return USER_PROFILE_PICTURE_PREFIX + userRegisterServiceModel.getUsername() + "." +
                Utils.getFileNameExtension(userRegisterServiceModel.getProfilePicture().getOriginalFilename());
    }
}
