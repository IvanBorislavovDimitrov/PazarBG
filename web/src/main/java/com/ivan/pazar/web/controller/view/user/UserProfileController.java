package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.model.service.MessagePageServiceModel;
import com.ivan.pazar.persistence.model.service.MessageServiceModel;
import com.ivan.pazar.persistence.service.api.MessageService;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.view.MessagePageViewModel;
import com.ivan.pazar.web.model.view.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserProfileController extends UserBaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;
    private final MessageService messageService;

    @Autowired
    public UserProfileController(UserService userService, ModelMapper modelMapper, UserConfiguration userConfiguration, MessageService messageService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
        this.messageService = messageService;
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(@RequestParam(value = "sentMessagePage", defaultValue = "0") int sentMessagesPage, @RequestParam(value = "receivedMessagePage", defaultValue = "0") int receivedMessagesPage, Model model) {
        String loggedUserUsername = userConfiguration.loggedUserUsername();
        PageRequest sentMessagesPageRequest = PageRequest.of(sentMessagesPage, WebConstants.DEFAULT_ELEMENTS_SIZE);
        PageRequest receivedMessagePagesRequest = PageRequest.of(receivedMessagesPage, WebConstants.DEFAULT_ELEMENTS_SIZE);
        UserProfileViewModel userProfileViewModel =
                modelMapper.map(userService.findUserByUsername(loggedUserUsername), UserProfileViewModel.class);
        trimMessages(userProfileViewModel);

        MessagePageViewModel sentMessagePageViewModel = modelMapper.map(messageService.findSentMessagesByUserUsername(loggedUserUsername, sentMessagesPageRequest), MessagePageViewModel.class);

        MessagePageViewModel  receivedMessagePageViewModel = modelMapper.map(messageService.findReceivedMessagesByUserUsername(loggedUserUsername, receivedMessagePagesRequest), MessagePageViewModel.class);

        model.addAttribute(WebConstants.TOTAL_SENT_MESSAGES_PAGES, new int[sentMessagePageViewModel.getPages()]);
        model.addAttribute(WebConstants.TOTAL_SENT_MESSAGES_PAGES_COUNT, sentMessagePageViewModel.getPages());
        model.addAttribute(WebConstants.SENT_MESSAGE_PAGE, sentMessagesPage);
        model.addAttribute(WebConstants.SENT_MESSAGES, sentMessagePageViewModel.getMessageServiceModels());

        model.addAttribute(WebConstants.TOTAL_RECEIVED_MESSAGES_PAGES, new int[receivedMessagePageViewModel.getPages()]);
        model.addAttribute(WebConstants.TOTAL_RECEIVED_MESSAGES_PAGES_COUNT, receivedMessagePageViewModel.getPages());
        model.addAttribute(WebConstants.RECEIVED_MESSAGE_PAGE, receivedMessagesPage);
        model.addAttribute(WebConstants.RECEIVED_MESSAGES, receivedMessagePageViewModel.getMessageServiceModels());

        model.addAttribute(WebConstants.USER, userProfileViewModel);

        return renderView(WebConstants.VIEWS_USER_PROFILE, model);
    }

    @GetMapping("/other-user-profile")
    public ModelAndView otherUserProfile(@RequestParam("username") String username, Model model) {
        UserProfileViewModel userProfileViewModel =
                modelMapper.map(userService.findUserByUsername(username), UserProfileViewModel.class);
        model.addAttribute(WebConstants.USER, userProfileViewModel);

        return renderView(WebConstants.VIEWS_USER_PROFILE, model);
    }

    private void trimMessages(UserProfileViewModel userProfileViewModel) {
        userProfileViewModel.getReceivedMessages().forEach(message -> message.setContent(message.getContent().substring(0, Math.min(message.getContent().length(), WebConstants.DEFAULT_MESSAGE_SIZE))));
        userProfileViewModel.getSentMessages().forEach(message -> message.setContent(message.getContent().substring(0, Math.min(message.getContent().length(), WebConstants.DEFAULT_MESSAGE_SIZE))));
    }
}
