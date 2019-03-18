package com.ivan.pazar.web.controller.view.admin;

import com.ivan.pazar.web.controller.view.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/admin")
public abstract class AdminBaseController extends BaseController {
}
