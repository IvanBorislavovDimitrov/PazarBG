package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.web.controller.view.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/users")
@PreAuthorize("isAuthenticated()")
public abstract class UserBaseController extends BaseController {
}
