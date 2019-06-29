package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.ContactUsMessage;
import com.ivan.pazar.persistence.model.service.ContactUsMessageServiceModel;
import com.ivan.pazar.persistence.repository.ContactUsMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContactUsMessageServiceTests {

    @Mock
    private ContactUsMessageRepository contactUsMessageRepository;

    private ModelMapper modelMapper;
    private ContactUsMessageServiceImpl contactUsMessageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        contactUsMessageService = new ContactUsMessageServiceImpl(contactUsMessageRepository, modelMapper);
    }

    @Test
    public void contactUsMessageService_test_messageSaved() {
        when(contactUsMessageRepository.save(any())).thenReturn(mock(ContactUsMessage.class));
        contactUsMessageService.save(mock(ContactUsMessageServiceModel.class));
        verify(contactUsMessageRepository).save(any());
    }
}
