package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.AdvertisementServiceModel;
import com.ivan.pazar.repository.AdvertisementRepository;
import com.ivan.pazar.service.api.AdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final ModelMapper modelMapper;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, ModelMapper modelMapper) {
        this.advertisementRepository = advertisementRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AdvertisementServiceModel save(AdvertisementServiceModel advertisementServiceModel) {
        return null;
    }
}
