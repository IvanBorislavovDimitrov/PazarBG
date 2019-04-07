package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.user.ProfilePictureManager;
import com.ivan.pazar.persistence.dao.videos.VideoManager;
import com.ivan.pazar.persistence.exceptions.user.EmailTakenException;
import com.ivan.pazar.persistence.exceptions.user.InvalidPasswordException;
import com.ivan.pazar.persistence.exceptions.user.PasswordsMismatchException;
import com.ivan.pazar.persistence.exceptions.user.PhoneNumberTakenException;
import com.ivan.pazar.persistence.model.service.*;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
import com.ivan.pazar.persistence.repository.UserRepository;
import com.ivan.pazar.persistence.service.service_api.RegionServiceExtended;
import com.ivan.pazar.persistence.service.service_api.RoleServiceExtended;
import com.ivan.pazar.persistence.service.service_api.TownServiceExtended;
import com.ivan.pazar.persistence.service.service_api.UserServiceExtended;
import com.ivan.pazar.persistence.util.Utils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_PROFILE_PICTURE_PREFIX = "profile_picture_";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    private final RoleServiceExtended roleService;
    private final RegionServiceExtended regionService;
    private final TownServiceExtended townService;
    private final ProfilePictureManager profilePictureManager;
    private final AdvertisementPicturesManager advertisementPicturesManager;
    private final VideoManager videoManager;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleServiceImpl roleService, RegionServiceImpl regionService, TownServiceImpl townService, ProfilePictureManager profilePictureManager, AdvertisementPicturesManager advertisementPicturesManager, VideoManager videoManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.regionService = regionService;
        this.townService = townService;
        this.profilePictureManager = profilePictureManager;
        this.advertisementPicturesManager = advertisementPicturesManager;
        this.videoManager = videoManager;
    }

    @Override
    public UserServiceModel save(UserServiceBindingModel userServiceBindingModel) {
        LOGGER.info(Messages.ADDING_USER);
        checkUserServiceModelValid(userServiceBindingModel);
        User user = modelMapper.map(userServiceBindingModel, User.class);
        String profilePictureName = getProfilePictureName(userServiceBindingModel);
        user.setProfilePictureName(profilePictureName);
        if (profilePictureName != null) {
            user.setProfilePictureName(profilePictureName);
            executeInNewThread(() -> savePicture(profilePictureName, userServiceBindingModel.getProfilePicture()));
        }

        if (userRepository.count() == 0) {
            user.getRoles().add(roleService.getByUserRole(UserRole.ROLE_ADMIN));
            user.getRoles().add(roleService.getByUserRole(UserRole.ROLE_USER));
            user.getRoles().add(roleService.getByUserRole(UserRole.ROLE_MODERATOR));
            user.getRoles().add(roleService.getByUserRole(UserRole.ROLE_ROOT));
            user.setActive(true);
        } else {
            user.getRoles().add(roleService.getByUserRole(UserRole.ROLE_USER));
        }
        user.setRegion(regionService.findByName(userServiceBindingModel.getRegion()));
        user.setTown(townService.findByName(userServiceBindingModel.getTown()));
        user.setRegisteredAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userServiceBindingModel.getPassword()));
        User savedUser = userRepository.saveAndFlush(user);

        return modelMapper.map(savedUser, UserServiceModel.class);
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
        User user = userRepository.findByUsername(username).orElse(null);

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setAdvertisements(user.getAdvertisements()
                .stream()
                .map(advertisement -> modelMapper.map(advertisement, AdvertisementServiceModel.class))
                .collect(Collectors.toList()));
        userServiceModel.setDescription(user.getDescription());
        userServiceModel.setEmail(user.getEmail());
        userServiceModel.setFirstName(user.getFirstName());
        userServiceModel.setLastName(user.getLastName());
        userServiceModel.setUsername(user.getUsername());
        userServiceModel.setPhoneNumber(user.getPhoneNumber());
        userServiceModel.setProfilePictureName(user.getProfilePictureName());
        userServiceModel.setReceivedMessages(user.getReceivedMessages().stream()
                .map(message -> modelMapper.map(message, MessageServiceModel.class))
                .collect(Collectors.toList()));
        userServiceModel.setSentMessages(user.getSentMessages()
                .stream()
                .map(message -> modelMapper.map(message, MessageServiceModel.class))
                .collect(Collectors.toList()));
        userServiceModel.setRegion(modelMapper.map(user.getRegion(), RegionServiceModel.class));
        userServiceModel.setTown(modelMapper.map(user.getTown(), TownServiceModel.class));
        userServiceModel.setReviews(user.getReviews().stream()
                .map(review -> modelMapper.map(review, ReviewServiceModel.class))
                .collect(Collectors.toList()));
        userServiceModel.setWebsiteAddress(user.getWebsiteAddress());

        mapMessages(user, userServiceModel);

        return userServiceModel;
    }

    @Override
    public void updateUser(String loggedUserUsername, UserServiceBindingModel userServiceBindingModel) {
        User user = userRepository.findByUsername(loggedUserUsername).orElse(null);
        if (!canUpdateEmail(user, userServiceBindingModel.getEmail())) {
            EmailTakenException emailTakenException = new EmailTakenException();
            LOGGER.error(emailTakenException.toString());
            throw emailTakenException;
        }
        if (!canUpdatePhoneNumber(user, userServiceBindingModel.getPhoneNumber())) {
            PhoneNumberTakenException phoneNumberTakenException = new PhoneNumberTakenException();
            LOGGER.error(phoneNumberTakenException.toString());
            throw phoneNumberTakenException;
        }
        user.setEmail(userServiceBindingModel.getEmail());
        user.setFirstName(userServiceBindingModel.getFirstName());
        user.setLastName(userServiceBindingModel.getLastName());
        user.setPhoneNumber(userServiceBindingModel.getPhoneNumber());
        user.setWebsiteAddress(userServiceBindingModel.getWebsiteAddress());
        user.setDescription(userServiceBindingModel.getDescription());
        user.setRegion(regionService.findByName(userServiceBindingModel.getRegion()));
        user.setTown(townService.findByName(userServiceBindingModel.getTown()));

        userRepository.saveAndFlush(user);
    }

    @Override
    public void tryUpdatePassword(String loggedUserUsername, UserChangePassword userChangePassword) {
        User user = userRepository.findByUsername(loggedUserUsername).orElse(null);
        deleteRelatedContent(user);

        if (!passwordEncoder.matches(userChangePassword.getNewPassword(), user.getPassword())) {
            InvalidPasswordException invalidPasswordException = new InvalidPasswordException();
            LOGGER.error(invalidPasswordException.toString());
            throw invalidPasswordException;
        }
        if (!userChangePassword.getNewPassword().equals(userChangePassword.getConfirmPassword())) {
            PasswordsMismatchException passwordsMismatchException = new PasswordsMismatchException();
            LOGGER.error(passwordsMismatchException.toString());
            throw passwordsMismatchException;
        }

        user.setPassword(passwordEncoder.encode(userChangePassword.getNewPassword()));
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
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
        String profilePictureName = USER_PROFILE_PICTURE_PREFIX + username + "." +
                Utils.getFileNameExtension(picture.getOriginalFilename());

        executeInNewThread(() -> savePicture(profilePictureName, picture));
        user.setProfilePictureName(profilePictureName);
        userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserServiceModel> findAllByUsernameContaining(String prefix) {
        return userRepository.findAllByUsernameContaining(prefix).stream()
                .filter(user -> user.getRoles().stream().noneMatch(role -> role.getUserRole() == UserRole.ROLE_ROOT))
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserRole(UserChangeRoleServiceModel userChangeRoleServiceModel) {
        User user = userRepository.findByUsername(userChangeRoleServiceModel.getUsername()).orElse(null);
        Set<Role> roles = new HashSet<>();
        userChangeRoleServiceModel.getRoles()
                .forEach(userRole -> {
                    Role role = roleService.getByUserRole(userRole);
                    roles.add(role);
                });

        user.setRoles(roles);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user.getProfilePictureName() != null) {
            try {
                profilePictureManager.deletePictureIfExists(user.getProfilePictureName());
            } catch (IOException e) {
                LOGGER.error(e.toString());
                e.printStackTrace();
            }
        }
        userRepository.delete(user);
    }

    @Override
    public Set<String> getRolesForUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user.getRoles().stream()
                .map(role -> role.getUserRole().toString())
                .collect(Collectors.toSet());
    }

    @Override
    public void activateUser(String id) {
        User user = userRepository.findById(id).orElse(null);

        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public void deleteNonActivatedUsers() {
        userRepository.deleteAllByActiveIsFalse();
    }

    @Override
    public List<String> getUsersEmails(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    private void mapMessages(User user, UserServiceModel userServiceModel) {
        userServiceModel.getSentMessages().clear();
        user.getSentMessages()
                .stream()
                .filter(message -> !message.isHidden())
                .forEach(sentMessage -> {
                    User receiver = sentMessage.getReceiver();
                    MessageServiceModel messageServiceModel = modelMapper.map(sentMessage, MessageServiceModel.class);
                    messageServiceModel.setReceiver(modelMapper.map(receiver, UserServiceModel.class));
                    userServiceModel.getSentMessages().add(messageServiceModel);
                });
        userServiceModel.getReceivedMessages().clear();
        user.getReceivedMessages()
                .forEach(receivedMessage -> {
                    User sender = receivedMessage.getSender();
                    MessageServiceModel messageServiceModel = modelMapper.map(receivedMessage, MessageServiceModel.class);
                    messageServiceModel.setSender(modelMapper.map(sender, UserServiceModel.class));
                    userServiceModel.getReceivedMessages().add(messageServiceModel);
                });
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

    private void executeInNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    private void savePicture(String profilePictureName, MultipartFile multipartPicture) {
        byte[] bytes;
        try {
            bytes = multipartPicture.getBytes();
            profilePictureManager.saveProfilePicture(profilePictureName, bytes);
        } catch (IOException e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
    }

    private void checkUserServiceModelValid(UserServiceBindingModel userRegisterServiceModel) {
        if (!userRegisterServiceModel.getPassword().equals(userRegisterServiceModel.getConfirmPassword())) {
            PasswordsMismatchException passwordsMismatchException = new PasswordsMismatchException();
            LOGGER.error(passwordsMismatchException.toString());
            throw passwordsMismatchException;
        }
    }

    private String getProfilePictureName(UserServiceBindingModel userRegisterServiceModel) {
        if (userRegisterServiceModel.getProfilePicture().isEmpty()) {
            return null;
        }
        return USER_PROFILE_PICTURE_PREFIX + userRegisterServiceModel.getUsername() + "." +
                Utils.getFileNameExtension(userRegisterServiceModel.getProfilePicture().getOriginalFilename());
    }

    private void deleteRelatedContent(User user) {
        List<Advertisement> advertisements = user.getAdvertisements();
        advertisements.forEach(advertisement -> {
            advertisementPicturesManager.deletePicturesIfExist(advertisement.getPictures());
            if (advertisement.getVideo() != null) {
                videoManager.deleteVideo(advertisement.getVideo().getName());
            }
        });
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
