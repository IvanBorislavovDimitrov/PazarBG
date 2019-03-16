package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.persistence.model.service.RegionServiceModel;
import com.ivan.pazar.persistence.model.service.rest.RegionRestServiceModel;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.service.api.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

    @Override
    public List<RegionRestServiceModel> getAllRegionsRest() {
        return regionRepository.findAll()
                .stream()
                .map(region -> modelMapper.map(region, RegionRestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Region findByName(String name) {
        return regionRepository.findByName(name);
    }
}
