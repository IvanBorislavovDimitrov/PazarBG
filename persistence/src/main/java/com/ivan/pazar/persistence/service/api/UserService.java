package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.UserChangePassword;
import com.ivan.pazar.persistence.model.service.UserChangeRoleServiceModel;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserServiceModel save(UserServiceBindingModel userServiceBindingModel);

    boolean isEmailFree(String email);

    boolean isUsernameFree(String username);

    boolean isPhoneNumberFree(String phoneNumber);

    UserServiceModel findUserByUsername(String username);

    void updateUser(String loggedUserUsername, UserServiceBindingModel userServiceBindingModel);

    void tryUpdatePassword(String loggedUserUsername, UserChangePassword userChangePassword);

    void updateUserPicture(String username, MultipartFile picture);

    List<UserServiceModel> findAllByUsernameContaining(String prefix);

    void updateUserRole(UserChangeRoleServiceModel map);
}
