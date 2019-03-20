package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.persistence.model.service.TownServiceModel;
import com.ivan.pazar.persistence.model.service.rest.TownRestServiceModel;
import com.ivan.pazar.persistence.repository.TownRepository;
import com.ivan.pazar.persistence.service.api.TownService;
import com.ivan.pazar.persistence.service.service_api.TownServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TownServiceImpl implements TownServiceExtended {

    private static final String INIT_TOWN = "Sofia";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final RegionServiceImpl regionService;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, RegionServiceImpl regionService) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.regionService = regionService;
    }

    @PostConstruct
    public void init() {
        if (townRepository.count() == 0) {
            Town town = new Town();
            town.setName(INIT_TOWN);
            Region region = regionService.findByName(RegionServiceImpl.INIT_REGION);
            town.setRegion(region);

            townRepository.save(town);
        }
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

    public Town findByName(String town) {
        return townRepository.findByName(town);
    }

    @Override
    public Town getTownByName(String town) {
        return townRepository.findByName(town);
    }
}
