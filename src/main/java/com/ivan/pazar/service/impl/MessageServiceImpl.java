package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.MessageServiceModel;
import com.ivan.pazar.repository.MessageRepository;
import com.ivan.pazar.service.api.MessageService;
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
