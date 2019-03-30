package com.ivan.pazar.web.controller.view;

import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.service.impl.EmailServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    @GetMapping(value = "/")
    public ModelAndView home(Model model) {
        return renderView(ViewConstants.VIEW_INDEX, model);
    }

}
