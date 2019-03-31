package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;
import com.ivan.pazar.persistence.model.service.MessageServiceModel;

public interface MessageService {

    void sendMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername);

    void deleteById(String messageId);

    MessageServiceModel findById(String id);

    void replyMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername, String sender);

    void hide(String messageId);
}
