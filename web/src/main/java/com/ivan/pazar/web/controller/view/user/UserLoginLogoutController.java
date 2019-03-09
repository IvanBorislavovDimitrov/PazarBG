package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.controller.view.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/users")
public class UserLoginLogoutController extends BaseController {

    @GetMapping("/login")
    public ModelAndView login(Model model) {
        return renderView(ViewConstants.VIEWS_USER_LOGIN, model);
    }

    @GetMapping(value = "/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return redirect(ViewConstants.REDIRECT_INDEX);
    }
}
