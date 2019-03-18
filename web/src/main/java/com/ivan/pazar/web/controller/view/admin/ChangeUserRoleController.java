package com.ivan.pazar.web.controller.view.admin;

import com.ivan.pazar.persistence.model.service.UserChangeRoleServiceModel;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.binding.UserChangeRoleBindingModel;
import com.ivan.pazar.web.model.binding.UserEditBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChangeUserRoleController extends AdminBaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ChangeUserRoleController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/change-user-role")
    public ModelAndView changeUserRole(Model model) {

        return renderView(ViewConstants.VIEWS_CHANGE_USER_ROLE, model);
    }

    @GetMapping("/change-user-role-confirm")
    public ModelAndView adminChangeRoleConfirm(@RequestParam("username") String username, Model model) {
        model.addAttribute(ViewConstants.USERNAME, username);

        return renderView(ViewConstants.VIEWS_CHANGE_USER_ROLE_CONFIRM, model);
    }

    @PostMapping("/change-user-role-confirm")
    public ModelAndView adminChangeRoleConfirm(UserChangeRoleBindingModel userChangeRoleBindingModel, Model model) {
        userService.updateUserRole(modelMapper.map(userChangeRoleBindingModel, UserChangeRoleServiceModel.class));

        return redirect(ViewConstants.REDIRECT_ADMIN_CHANGE);
    }
}
