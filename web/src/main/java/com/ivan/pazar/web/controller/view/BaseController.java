package com.ivan.pazar.web.controller.view;

import com.ivan.pazar.web.constants.WebConstants;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    private static final String BASE_LAYOUT = "base-layout";
    private static final String REDIRECT = "redirect:/";

    protected static final String VIEW = "view";

    protected ModelAndView renderView(String view, Model model) {
        model.addAttribute(VIEW, view);

        return new ModelAndView(BASE_LAYOUT);
    }

    protected ModelAndView redirect(String route) {
        return new ModelAndView(REDIRECT + route);
    }

}
