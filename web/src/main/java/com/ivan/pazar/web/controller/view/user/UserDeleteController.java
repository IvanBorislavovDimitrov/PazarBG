package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.ViewConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserDeleteController extends UserBaseController {

    private final UserService userService;
    private final UserConfiguration userConfiguration;

    @Autowired
    public UserDeleteController(UserService userService, UserConfiguration userConfiguration) {
        this.userService = userService;
        this.userConfiguration = userConfiguration;
    }

    @GetMapping("/delete")
    public ModelAndView deleteProfile(Model model) {

        return renderView(ViewConstants.VIEWS_DELETE_PROFILE, model);
    }

    @PostMapping("/delete")
    public ModelAndView deleteProfileConfirm(HttpSession httpSession) {
        userService.deleteByUsername(userConfiguration.loggedUserUsername());
        httpSession.invalidate();

        return redirect(ViewConstants.REDIRECT_INDEX);
    }
}
