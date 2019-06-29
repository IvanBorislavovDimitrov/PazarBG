package com.ivan.pazar.web.controller.view.about;

import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.controller.view.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutUsController extends BaseController {

    @GetMapping("/about-us")
    public ModelAndView aboutUs(Model model) {
        return renderView(WebConstants.VIEWS_ABOUT_US, model);
    }
}
