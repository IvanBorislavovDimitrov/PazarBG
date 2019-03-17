package com.ivan.pazar.web.controller.view.admin;

import com.ivan.pazar.web.constants.ViewConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController extends AdminBaseController {

    @GetMapping("/panel")
    public ModelAndView adminView(Model model) {

        return renderView(ViewConstants.VIEWS_ADMIN_PANEL, model);
    }
}
