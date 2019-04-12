package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.domain.model.enums.UserRole;
import com.ivan.pazar.persistence.dao.advertisements.AdvertisementPicturesManager;
import com.ivan.pazar.persistence.dao.user.ProfilePictureManager;
import com.ivan.pazar.persistence.dao.videos.VideoManager;
import com.ivan.pazar.persistence.model.service.UserChangePassword;
import com.ivan.pazar.persistence.model.service.UserChangeRoleServiceModel;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
import com.ivan.pazar.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private RegionServiceImpl regionService;

    @Mock
    private TownServiceImpl townService;

    @Mock
    private ProfilePictureManager profilePictureManager;

    @Mock
    private AdvertisementPicturesManager advertisementPicturesManager;

    @Mock
    private VideoManager videoManager;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        userService = new UserServiceImpl(userRepository, modelMapper, passwordEncoder, roleService, regionService, townService, profilePictureManager, advertisementPicturesManager, videoManager);
    }

    @Test
    public void userService_save_userSaved() {
        UserServiceBindingModel userServiceBindingModel = mock(UserServiceBindingModel.class);
        String username = "user";
        String password = "123123123";
        String email = "email@email.email";
        when(userServiceBindingModel.getUsername()).thenReturn(username);
        when(userServiceBindingModel.getPassword()).thenReturn(password);
        when(userServiceBindingModel.getConfirmPassword()).thenReturn(password);
        when(userServiceBindingModel.getEmail()).thenReturn(email);
        when(userServiceBindingModel.getProfilePicture()).thenReturn(mock(MultipartFile.class));
        when(userRepository.saveAndFlush(any())).thenReturn(mock(User.class));
        userService.save(userServiceBindingModel);
        verify(userRepository).saveAndFlush(any());
    }

    @Test
    public void userService_isEmailFree_expectFree() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        assertTrue(userService.isEmailFree("123"));
    }

    @Test
    public void userService_isUsernameFree_expectFree() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        assertTrue(userService.isEmailFree("123"));
    }

    @Test
    public void userService_isPhoneNumberFree_expectFree() {
        when(userRepository.existsByPhoneNumber(any())).thenReturn(false);
        assertTrue(userService.isEmailFree("123"));
    }

    @Test
    public void userService_findUserByUsername_userFound() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("user");
        when(user.getRegion()).thenReturn(mock(Region.class));
        when(user.getTown()).thenReturn(mock(Town.class));
        when(user.getReviews()).thenReturn(Collections.emptyList());
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);
        UserServiceModel user1 = userService.findUserByUsername("user");
        assertEquals("user", user1.getUsername());
    }

    @Test
    public void userService_updateUser_userUpdated() {
        User user = mock(User.class);
        UserServiceBindingModel userServiceBindingModel = mock(UserServiceBindingModel.class);
        String email = "email";
        when(user.getEmail()).thenReturn(email);
        when(user.getPhoneNumber()).thenReturn("123");
        when(user.getUsername()).thenReturn("user");
        when(userServiceBindingModel.getEmail()).thenReturn(email);
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);
        String loggedUserUsername = "user";
        when(user.getEmail()).thenReturn(email);
        userService.updateUser(loggedUserUsername, userServiceBindingModel);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void userService_tryUpdatePassword_passwordUpdated() {
        User user = mock(User.class);
        when(user.getAdvertisements()).thenReturn(Collections.emptyList());
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);
        UserChangePassword userChangePassword = mock(UserChangePassword.class);
        when(user.getPassword()).thenReturn("123");
        when(userChangePassword.getConfirmPassword()).thenReturn("123");
        when(userChangePassword.getNewPassword()).thenReturn("123");
        when(userChangePassword.getConfirmPassword()).thenReturn("123");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        userService.tryUpdatePassword("user", userChangePassword);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void userService_updateUserProfilePicture() {
        User user = mock(User.class);
        when(user.getProfilePictureName()).thenReturn("picture.jpg");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        MultipartFile picture = mock(MultipartFile.class);
        when(picture.getOriginalFilename()).thenReturn("po.jpg");
        userService.updateUserPicture("user", picture);
        verify(userRepository).saveAndFlush(any());
    }

    @Test
    public void userService_findAllByUsername_userFound() {
        User user = mock(User.class);
        Role role = mock(Role.class);
        when(role.getUserRole()).thenReturn(UserRole.ROLE_ADMIN);
        when(user.getRoles()).thenReturn(Stream.of(role).collect(Collectors.toSet()));
        when(userRepository.findAllByUsernameContaining(anyString())).thenReturn(Arrays.asList(user));
        assertEquals(1, userService.findAllByUsernameContaining("123").size());
    }

    @Test
    public void userService_updateUserRole_roleUpdated() {
        User user = mock(User.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(user.getRoles()).thenReturn(Collections.emptySet());
        UserChangeRoleServiceModel userChangeRoleServiceModel = mock(UserChangeRoleServiceModel.class);
        when(userChangeRoleServiceModel.getUsername()).thenReturn("user");
        userService.updateUserRole(userChangeRoleServiceModel);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void userService_deleteByUsername_expectDeleted() {
        User user = mock(User.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        userService.deleteByUsername("user");
        verify(userRepository).delete(user);
    }

    @Test
    public void userService_getRolesForUser_rolesReturned() {
        User user = mock(User.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(user.getRoles()).thenReturn(Collections.emptySet());
        assertEquals(0, userService.getRolesForUser("user").size());
    }

    @Test
    public void userService_activateUser_userActivated() {
        User user = mock(User.class);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        userService.activateUser("123");
        verify(user).setActive(true);
        verify(userRepository).save(any());
    }

    @Test
    public void userService_deleteNonActivatedUsers_usersDeleted() {
        userService.deleteNonActivatedUsers();
        verify(userRepository).deleteAllByActiveIsFalse();
    }

    @Test
    public void userService_getUsersEmail_emailsReturned() {
        PageRequest pageRequest = mock(PageRequest.class);
        userRepository.findAll(pageRequest);
        Page<User> userPage = mock(Page.class);
        when(userRepository.findAll(pageRequest)).thenReturn(userPage);
        assertEquals(0, userService.getUsersEmails(pageRequest).size());
    }

    @Test
    public void userService_getUserByUsername_userReturned() {
        User user = mock(User.class);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        User u = userService.getUserByUsername("usere");
        assertEquals(user, u);
    }

}
