package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.TownServiceModel;
import com.ivan.pazar.repository.TownRepository;
import com.ivan.pazar.service.api.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
}
