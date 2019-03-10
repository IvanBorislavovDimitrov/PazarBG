package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.web.constants.ViewConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserProfileController extends UserBaseController {

    @GetMapping("/profile")
    public ModelAndView profile(Model model) {
        return renderView(ViewConstants.VIEWS_USER_PROFILE, model);
    }

}
