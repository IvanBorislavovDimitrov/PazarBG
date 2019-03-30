package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;

public interface MessageService {

    void sendMessage(String advertId, MessageAddServiceModel messageAddServiceModel, String loggedUserUsername);
}
