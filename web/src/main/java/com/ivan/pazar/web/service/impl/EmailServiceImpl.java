package com.ivan.pazar.web.service.impl;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import com.ivan.pazar.web.service.api.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String DEFAULT_MAIL_SENDER = "automaticMailSenderCommunity@gmail.com";
    private static final String REGISTER_MESSAGE = "Thank you for registering  in our website Bazar-BG. We will ensure you with the best user experience you have ever imagined!%s Kind regards!";
    private static final String GREETINGS = "Greetings, %s.";
    private static final String DAILY_MESSAGE = "Hello! Visit our website Bazar-BG and publish a new advertisement";
    private static final int ONE_DAY = 1000 * 60 * 60 * 24;

    private final JavaMailSender javaMailSender;
    private final UserService userService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    @Override
    public void sendNotificationForRegistering(UserRegisterBindingModel userRegisterBindingModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userRegisterBindingModel.getEmail());
        simpleMailMessage.setFrom(DEFAULT_MAIL_SENDER);

        String subject = String.format(GREETINGS, userRegisterBindingModel.getUsername());
        simpleMailMessage.setSubject(subject);

        String text = String.format(REGISTER_MESSAGE, System.lineSeparator());
        simpleMailMessage.setText(text);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Scheduled(fixedRate = ONE_DAY)
//    @Async
    public void sendDailyNotifications() {
        int page = 0;
        PageRequest pageRequest = PageRequest.of(page, WebConstants.DEFAULT_USERS_SIZE, Sort.by(Sort.Order.asc(WebConstants.ID)));
        List<String> usersEmails = userService.getUsersEmails(pageRequest);
        while (!usersEmails.isEmpty()) {
            notifyUsers(usersEmails);
            page++;
            pageRequest = PageRequest.of(WebConstants.DEFAULT_USERS_SIZE, page);
            usersEmails = userService.getUsersEmails(pageRequest);
        }
    }

    private void notifyUsers(List<String> usersEmails) {
        usersEmails.forEach(this::sendDefaultMessage);
    }

    private void sendDefaultMessage(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(DEFAULT_MAIL_SENDER);
        simpleMailMessage.setText(EmailServiceImpl.DAILY_MESSAGE);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
