package com.ivan.pazar.web.controller.view.message;

import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;
import com.ivan.pazar.persistence.service.api.MessageService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.view.MessageViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController extends MessageBaseController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper, UserConfiguration userConfiguration) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
    }

    @GetMapping("/reply")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView viewAndReply(@RequestParam("messageId") String messageId, @RequestParam("sender") String sender, Model model) {
        MessageViewModel messageViewModel = modelMapper.map(messageService.findById(messageId), MessageViewModel.class);

        model.addAttribute(WebConstants.MESSAGE, messageViewModel);
        model.addAttribute(WebConstants.SENDER, sender);

        return renderView(WebConstants.VIEWS_VIEW_AND_REPLY_MESSAGE, model);
    }

    @PostMapping("/reply")
    public ModelAndView replyMessage(@RequestParam("advertId") String advertId, @RequestParam("sender") String sender,
                                     MessageViewModel messageViewModel) {
        messageService.replyMessage(advertId, modelMapper.map(messageViewModel,
                MessageAddServiceModel.class), userConfiguration.loggedUserUsername(), sender);

        return redirect(WebConstants.REDIRECT_USER_PROFILE);
    }

    @PostMapping("/delete")
    public ModelAndView deleteMessageConfirm(@RequestParam("messageId") String messageId) {
        messageService.hide(messageId);

        return redirect(WebConstants.REDIRECT_USER_PROFILE);
    }


}
