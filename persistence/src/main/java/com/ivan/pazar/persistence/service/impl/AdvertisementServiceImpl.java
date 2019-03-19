package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.model.service.AdvertisementServiceModel;
import com.ivan.pazar.persistence.repository.AdvertisementRepository;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.persistence.service.service_api.AdvertisementServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementServiceExtended {

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
