package com.ivan.pazar.web.controller.view.region;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegionAddController extends RegionBaseController {

    @GetMapping("/add")
    public ModelAndView addRegion(Model model) {

        return renderView("views/regions/add-region", model);
    }
}
