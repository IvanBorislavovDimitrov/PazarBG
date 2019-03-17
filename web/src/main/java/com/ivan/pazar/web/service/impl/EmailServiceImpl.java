package com.ivan.pazar.web.service.impl;

import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import com.ivan.pazar.web.service.api.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String DEFAULT_MAIL_SENDER = "automaticMailSenderCommunity@gmail.com";
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendNotificationForRegistering(UserRegisterBindingModel userRegisterBindingModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userRegisterBindingModel.getEmail());
        simpleMailMessage.setFrom(DEFAULT_MAIL_SENDER);

        String subject = String.format("Greetings, %s.", userRegisterBindingModel.getUsername());
        simpleMailMessage.setSubject(subject);

        String text = String.format("Thank you for registering  in our website Bazar-BG. We will ensure you with the best user experience you have ever imagined!%s Kind regards!", System.lineSeparator());
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }
}
