package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.ContactUsMessage;
import com.ivan.pazar.persistence.model.service.ContactUsMessageServiceModel;
import com.ivan.pazar.persistence.repository.ContactUsMessageRepository;
import com.ivan.pazar.persistence.service.api.ContactUsMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactUsMessageServiceImpl implements ContactUsMessageService {

    private final ContactUsMessageRepository contactUsMessageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactUsMessageServiceImpl(ContactUsMessageRepository contactUsMessageRepository, ModelMapper modelMapper) {
        this.contactUsMessageRepository = contactUsMessageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ContactUsMessageServiceModel save(ContactUsMessageServiceModel contactUsMessageServiceModel) {
        ContactUsMessage contactUsMessage = modelMapper.map(contactUsMessageServiceModel, ContactUsMessage.class);
        ContactUsMessage saved = contactUsMessageRepository.save(contactUsMessage);

        return modelMapper.map(saved, ContactUsMessageServiceModel.class);
    }
}
