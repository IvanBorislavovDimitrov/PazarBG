package com.ivan.pazar.web.controller.view;

import com.ivan.pazar.web.constants.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.ivan.pazar.web.constants.WebConstants.REDIRECT_INDEX;
import static com.ivan.pazar.web.constants.WebConstants.VIEWS_ADVERTISE_REGISTER;

@Controller
public class HomeController extends BaseController {

    @GetMapping(value = "/")
    public ModelAndView home(Model model) {
        return renderView(WebConstants.VIEW_INDEX, model);
    }

    @GetMapping(value = "/advertise/register")
    public ModelAndView advertiseRegister() {

        return new ModelAndView(VIEWS_ADVERTISE_REGISTER);
    }

    @GetMapping(value = "/continue-as-guest")
    public ModelAndView continueAsGuest() {
        return redirect(REDIRECT_INDEX);
    }
}
