package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Message;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;
import com.ivan.pazar.persistence.model.service.MessageServiceModel;
import com.ivan.pazar.persistence.repository.MessageRepository;
import com.ivan.pazar.persistence.service.service_api.AdvertisementServiceExtended;
import com.ivan.pazar.persistence.service.service_api.MessageServiceExtended;
import com.ivan.pazar.persistence.service.service_api.UserServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class MessageServiceImpl implements MessageServiceExtended {

    private final MessageRepository messageRepository;
    private final AdvertisementServiceExtended advertisementService;
    private final UserServiceExtended userService;

    public MessageServiceImpl(MessageRepository messageRepository, AdvertisementServiceExtended advertisementService, UserServiceExtended userService) {
        this.messageRepository = messageRepository;
        this.advertisementService = advertisementService;
        this.userService = userService;
    }

    @Override
    public void sendMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername) {
        Advertisement advertisement = advertisementService.getAdvertisementById(advertId);
        if (advertisement == null) {
            return;
        }

        User user = userService.getUserByUsername(loggedUserUsername);

        Message message = new Message();
        message.setAddedOn(LocalDateTime.now());
        message.setContent(messageAddServiceModel.getMessage());
        message.setReceiver(advertisement.getAuthor());
        message.setSender(user);

        messageRepository.save(message);
    }
}
