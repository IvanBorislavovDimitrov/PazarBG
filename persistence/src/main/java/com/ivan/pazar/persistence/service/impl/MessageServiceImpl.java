package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.dto.service.MessageServiceModel;
import com.ivan.pazar.persistence.repository.MessageRepository;
import com.ivan.pazar.persistence.service.api.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageServiceModel save(MessageServiceModel messageServiceModel) {
        return null;
    }
}
