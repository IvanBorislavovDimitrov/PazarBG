package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Message;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;
import com.ivan.pazar.persistence.model.service.MessagePageServiceModel;
import com.ivan.pazar.persistence.model.service.MessageServiceModel;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.repository.MessageRepository;
import com.ivan.pazar.persistence.service.service_api.AdvertisementServiceExtended;
import com.ivan.pazar.persistence.service.service_api.MessageServiceExtended;
import com.ivan.pazar.persistence.service.service_api.UserServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final MessageRepository messageRepository;
    private final AdvertisementServiceExtended advertisementService;
    private final UserServiceExtended userService;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, AdvertisementServiceExtended advertisementService, UserServiceExtended userService, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.advertisementService = advertisementService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void sendMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername) {
        LOGGER.info(Messages.SENDING_MESSAGE);
        Advertisement advertisement = advertisementService.getAdvertisementById(advertId);
        if (advertisement == null) {
            LOGGER.error(Messages.ADVERTISEMENT_IS_NULL);
            return;
        }

        User user = userService.getUserByUsername(loggedUserUsername);

        Message message = new Message();
        message.setAddedOn(LocalDateTime.now());
        message.setContent(messageAddServiceModel.getContent());
        message.setReceiver(advertisement.getAuthor());
        message.setAdvertisement(advertisement);
        message.setSender(user);

        messageRepository.save(message);
    }

    @Override
    public void deleteById(String messageId) {
        messageRepository.deleteById(messageId);
    }

    @Override
    public void replyMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername, String sender) {
        Advertisement advertisement = advertisementService.getAdvertisementById(advertId);
        User senderUser = userService.getUserByUsername(sender);
        User loggedUser = userService.getUserByUsername(loggedUserUsername);
        Message message = new Message();
        message.setSender(loggedUser);
        message.setReceiver(senderUser);
        message.setAdvertisement(advertisement);
        message.setContent(messageAddServiceModel.getContent());
        message.setAddedOn(LocalDateTime.now());

        messageRepository.save(message);
    }

    @Override
    public void hide(String messageId) {
        Message message = messageRepository.findById(messageId).get();
        message.setHidden(true);
        messageRepository.saveAndFlush(message);
    }

    @Override
    public MessagePageServiceModel findSentMessagesByUserUsername(String loggedUserUsername, PageRequest sentMessagesPageRequest) {
        Page<Message> allBySenderUsername = messageRepository.findAllBySenderUsername(loggedUserUsername, sentMessagesPageRequest);

        return getMessagePageServiceModel(allBySenderUsername);
    }

    private MessagePageServiceModel getMessagePageServiceModel(Page<Message> messagePage) {
        MessagePageServiceModel messagePageServiceModel = new MessagePageServiceModel();
        messagePageServiceModel.setPages(messagePage.getTotalPages());
        messagePageServiceModel.setMessageServiceModels(messagePage.get()
                .map(message -> modelMapper.map(message, MessageServiceModel.class))
                .collect(Collectors.toList()));

        return messagePageServiceModel;
    }

    @Override
    public MessagePageServiceModel findReceivedMessagesByUserUsername(String loggedUserUsername, PageRequest receivedMessagePagesRequest) {
        Page<Message> allBySenderUsername = messageRepository.findAllByReceiverUsername(loggedUserUsername, receivedMessagePagesRequest);

        return getMessagePageServiceModel(allBySenderUsername);
    }

    @Override
    public MessageServiceModel findById(String id) {
        Message message = messageRepository.findById(id).get();
        MessageServiceModel messageServiceModel = modelMapper.map(message, MessageServiceModel.class);
        messageServiceModel.setSender(modelMapper.map(message.getSender(), UserServiceModel.class));
        messageServiceModel.setReceiver(modelMapper.map(message.getReceiver(), UserServiceModel.class));

        return messageServiceModel;
    }
}
