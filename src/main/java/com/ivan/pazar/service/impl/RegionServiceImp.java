package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.RegionServiceModel;
import com.ivan.pazar.repository.RegionRepository;
import com.ivan.pazar.service.api.RegionService;
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
