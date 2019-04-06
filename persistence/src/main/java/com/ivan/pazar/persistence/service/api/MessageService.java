package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;
import com.ivan.pazar.persistence.model.service.MessagePageServiceModel;
import com.ivan.pazar.persistence.model.service.MessageServiceModel;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface MessageService {

    void sendMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername);

    void deleteById(String messageId);

    MessageServiceModel findById(String id);

    void replyMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername, String sender);

    void hide(String messageId);

    MessagePageServiceModel findSentMessagesByUserUsername(String loggedUserUsername, PageRequest sentMessagesPageRequest);

    MessagePageServiceModel findReceivedMessagesByUserUsername(String loggedUserUsername, PageRequest receivedMessagePagesRequest);
}
