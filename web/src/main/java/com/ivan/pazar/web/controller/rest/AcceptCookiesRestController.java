package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.web.constants.WebConstants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AcceptCookiesRestController {

    @PostMapping("/cookies/accept")
    public void acceptCookies(HttpSession httpSession) {
        httpSession.setAttribute(WebConstants.COOKIES, true);
    }
}
