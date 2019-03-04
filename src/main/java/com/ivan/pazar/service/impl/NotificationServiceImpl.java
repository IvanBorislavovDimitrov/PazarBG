package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.NotificationServiceModel;
import com.ivan.pazar.repository.NotificationRepository;
import com.ivan.pazar.service.api.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationServiceModel save(NotificationServiceModel notificationServiceModel) {
        return null;
    }
}
