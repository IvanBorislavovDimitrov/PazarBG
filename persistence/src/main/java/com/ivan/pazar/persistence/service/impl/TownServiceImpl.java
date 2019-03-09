package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.dto.service.TownServiceModel;
import com.ivan.pazar.persistence.dto.service.rest.TownRestServiceModel;
import com.ivan.pazar.persistence.repository.TownRepository;
import com.ivan.pazar.persistence.service.api.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TownServiceModel save(TownServiceModel townServiceModel) {
        return null;
    }

    @Override
    public List<TownRestServiceModel> getAllByRegionRest(String region) {
        return townRepository.findAllByRegionNameLike(region)
                .stream()
                .map(town -> modelMapper.map(town, TownRestServiceModel.class))
                .collect(Collectors.toList());
    }
}
