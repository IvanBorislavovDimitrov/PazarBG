package com.ivan.pazar.web.controller.view;

import com.ivan.pazar.web.constants.ViewConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRegisterController extends BaseController {

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        return renderView(ViewConstants.VIEW_USER_REGISTER, model);
    }
}
