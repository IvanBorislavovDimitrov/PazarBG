package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.model.service.MessageServiceModel;
import com.ivan.pazar.persistence.repository.MessageRepository;
import com.ivan.pazar.persistence.service.api.MessageService;
import com.ivan.pazar.persistence.service.service_api.MessageServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageServiceImpl implements MessageServiceExtended {

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
