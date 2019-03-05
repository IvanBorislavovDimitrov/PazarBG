package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.dto.service.RegionServiceModel;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.service.api.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RegionServiceImp implements RegionService {

    private final RegionRepository regionRepository;
    private final ModelMapper modelMapper;

    public RegionServiceImp(RegionRepository regionRepository, ModelMapper modelMapper) {
        this.regionRepository = regionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RegionServiceModel save(RegionServiceModel regionServiceModel) {
        return null;
    }
}
