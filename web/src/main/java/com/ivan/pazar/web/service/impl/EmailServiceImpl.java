package com.ivan.pazar.web.service.impl;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import com.ivan.pazar.web.service.api.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String DEFAULT_MAIL_SENDER = "automaticMailSenderCommunity@gmail.com";
    private static final String REGISTER_MESSAGE = "Thank you for registering  in our website Bazar-BG. We will ensure you with the best user experience you have ever imagined!%s Kind regards!%sActivate: %s";
    private static final String GREETINGS = "Greetings, %s.";
    private static final String DAILY_MESSAGE = "Hello! Visit our website Bazar-BG and publish a new advertisement";
    private static final String ACTIVATE_LINK = "http://localhost:8000/users/activate/%s";
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USERNAME = "automaticmailsendercommunity@gmail.com";
    private static final String PASSWORD = "123456sender";
    private static final String TRANSPORT_PROTOCOL_KEY = "mail.transport.protocol";
    private static final String TRANSPORT_PROTOCOL_VALUE = "smtp";
    private static final String AUTH_KEY = "mail.smtp.auth";
    private static final String AUTH_VALUE = "false";
    private static final String STARTTLS_ENABLE_KEY = "mail.smtp.starttls.enable";
    private static final String STARTTLS_ENABLE_VALUE = "true";
    private static final String MAIL_DEBUG_KEY = "mail.debug";
    private static final String MAIL_DEBUG_VALUE = "true";

    private final JavaMailSenderImpl javaMailSender;
    private final UserService userService;

    @Autowired
    public EmailServiceImpl(UserService userService) {
        javaMailSender = new JavaMailSenderImpl();
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        javaMailSender.setHost(HOST);
        javaMailSender.setPort(PORT);

        javaMailSender.setUsername(USERNAME);
        javaMailSender.setPassword(PASSWORD);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put(TRANSPORT_PROTOCOL_KEY, TRANSPORT_PROTOCOL_VALUE);
        props.put(AUTH_KEY, AUTH_VALUE);
        props.put(STARTTLS_ENABLE_KEY, STARTTLS_ENABLE_VALUE);
        props.put(MAIL_DEBUG_KEY, MAIL_DEBUG_VALUE);
    }

    @Override
    @Async
    public void sendNotificationForRegistering(UserRegisterBindingModel userRegisterBindingModel, String id) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userRegisterBindingModel.getEmail());
        simpleMailMessage.setFrom(DEFAULT_MAIL_SENDER);


        String subject = String.format(GREETINGS, userRegisterBindingModel.getUsername());
        simpleMailMessage.setSubject(subject);
        String activationLink = String.format(ACTIVATE_LINK, id);

        String text = String.format(REGISTER_MESSAGE, System.lineSeparator(), System.lineSeparator(), activationLink);
        simpleMailMessage.setText(text);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = WebConstants.THREE_AM_EVERY_DAY)
    @Async
    @Override
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
