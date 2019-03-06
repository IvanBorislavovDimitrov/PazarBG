package com.ivan.pazar.web.controller.view;

import com.ivan.pazar.web.constants.ViewConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;

@Controller
public class HomeController extends BaseController {

    @GetMapping(value = "/")
    public ModelAndView home(Model model) {
        return renderView(ViewConstants.VIEW_INDEX, model);
    }
}
