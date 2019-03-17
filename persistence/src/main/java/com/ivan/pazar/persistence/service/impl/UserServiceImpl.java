package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.dao.ProfilePictureManager;
import com.ivan.pazar.persistence.exceptions.EmailTakenException;
import com.ivan.pazar.persistence.exceptions.InvalidPasswordException;
import com.ivan.pazar.persistence.exceptions.PasswordsMismatchException;
import com.ivan.pazar.persistence.exceptions.PhoneNumberTakenException;
import com.ivan.pazar.persistence.model.service.UserChangePassword;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String USER_PROFILE_PICTURE_PREFIX = "profile_picture_";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final RegionRepository regionRepository;
    private final TownRepository townRepository;
    private final ProfilePictureManager profilePictureManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, RegionRepository regionRepository, TownRepository townRepository, ProfilePictureManager profilePictureManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.regionRepository = regionRepository;
        this.townRepository = townRepository;
        this.profilePictureManager = profilePictureManager;
    }

    @Override
    public UserServiceModel save(UserServiceBindingModel userServiceBindingModel) {
        checkUserServiceModelValid(userServiceBindingModel);
        User user = modelMapper.map(userServiceBindingModel, User.class);
        String profilePictureName = getProfilePictureName(userServiceBindingModel);
        user.setProfilePictureName(profilePictureName);
        if (profilePictureName != null) {
            user.setProfilePictureName(profilePictureName);
            savePicture(profilePictureName, userServiceBindingModel.getProfilePicture());
        }

        if (userRepository.count() == 0) {
            user.getRoles().add(roleRepository.getByUserRole(UserRole.ROLE_ADMIN));
        } else {
            user.getRoles().add(roleRepository.getByUserRole(UserRole.ROLE_USER));
        }
        user.setRegion(regionRepository.findByName(userServiceBindingModel.getRegion()));
        user.setTown(townRepository.findByName(userServiceBindingModel.getTown()));

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

    @Override
    public void updateUser(String loggedUserUsername, UserServiceBindingModel userServiceBindingModel) {
        User user = userRepository.findByUsername(loggedUserUsername).orElse(null);
        if (!canUpdateEmail(user, userServiceBindingModel.getEmail())) {
            throw new EmailTakenException();
        }
        if (!canUpdatePhoneNumber(user, userServiceBindingModel.getPhoneNumber())) {
            throw new PhoneNumberTakenException();
        }
        user.setEmail(userServiceBindingModel.getEmail());
        user.setFirstName(userServiceBindingModel.getFirstName());
        user.setLastName(userServiceBindingModel.getLastName());
        user.setPhoneNumber(userServiceBindingModel.getPhoneNumber());
        user.setWebsiteAddress(userServiceBindingModel.getWebsiteAddress());
        user.setDescription(userServiceBindingModel.getDescription());
        user.setRegion(regionRepository.findByName(userServiceBindingModel.getRegion()));
        user.setTown(townRepository.findByName(userServiceBindingModel.getTown()));

        userRepository.saveAndFlush(user);
    }

    @Override
    public void tryUpdatePassword(String loggedUserUsername, UserChangePassword userChangePassword) {
        User user = userRepository.findByUsername(loggedUserUsername).orElse(null);
        if (!user.getPassword().equals(userChangePassword.getPassword())) {
            throw new InvalidPasswordException();
        }
        if (!userChangePassword.getNewPassword().equals(userChangePassword.getConfirmPassword())) {
            throw new PasswordsMismatchException();
        }

        user.setPassword(userChangePassword.getNewPassword());
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUserPicture(String username, MultipartFile picture) {
        User user = userRepository.findByUsername(username).orElse(null);
        String pictureName = user.getProfilePictureName();
        try {
            if (pictureName != null) {
                profilePictureManager.deletePictureIfExists(pictureName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String profilePictureName = USER_PROFILE_PICTURE_PREFIX + username + "." +
                Utils.getFileNameExtension(picture.getOriginalFilename());

        savePicture(profilePictureName, picture);
        user.setProfilePictureName(profilePictureName);
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserServiceModel> findAllByUsernameContaining(String prefix) {
        return userRepository.findAllByUsernameContaining(prefix).stream()
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    private boolean canUpdatePhoneNumber(User user, String phoneNumber) {
        if (user.getPhoneNumber().equals(phoneNumber)) {
            return true;
        }

        return isPhoneNumberFree(phoneNumber);
    }

    private boolean canUpdateEmail(User user, String email) {
        if (user.getEmail().equals(email)) {
            return true;
        }

        return isEmailFree(email);
    }

    private void savePicture(String profilePictureName, MultipartFile multipartPicture) {
        byte[] bytes;
        try {
            bytes = multipartPicture.getBytes();
            profilePictureManager.saveProfilePicture(profilePictureName, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUserServiceModelValid(UserServiceBindingModel userRegisterServiceModel) {
        if (!userRegisterServiceModel.getPassword().equals(userRegisterServiceModel.getConfirmPassword())) {
            throw new PasswordsMismatchException();
        }
    }

    private String getProfilePictureName(UserServiceBindingModel userRegisterServiceModel) {
        if (userRegisterServiceModel.getProfilePicture().isEmpty()) {
            return null;
        }
        return USER_PROFILE_PICTURE_PREFIX + userRegisterServiceModel.getUsername() + "." +
                Utils.getFileNameExtension(userRegisterServiceModel.getProfilePicture().getOriginalFilename());
    }
}
