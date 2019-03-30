package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.web.constants.ViewConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserLoginLogoutController extends UserBaseController {

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(@RequestParam(value = "error", defaultValue = "") String error, Model model) {
        if (error.equals(ViewConstants.TRUE)) {
            model.addAttribute(ViewConstants.LOGIN_ERROR, true);
        }
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
