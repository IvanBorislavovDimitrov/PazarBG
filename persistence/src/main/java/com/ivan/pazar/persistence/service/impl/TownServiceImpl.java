package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.TownAddServiceModel;
import com.ivan.pazar.persistence.model.service.TownServiceModel;
import com.ivan.pazar.persistence.model.service.rest.TownRestServiceModel;
import com.ivan.pazar.persistence.repository.TownRepository;
import com.ivan.pazar.persistence.service.service_api.RegionServiceExtended;
import com.ivan.pazar.persistence.service.service_api.TownServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TownServiceImpl implements TownServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(TownServiceImpl.class);

    private static final String INIT_TOWN = "Sofia";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final RegionServiceExtended regionService;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, RegionServiceExtended regionService) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.regionService = regionService;
    }

    @PostConstruct
    public void init() {
        if (townRepository.count() == 0) {
            LOGGER.info(Messages.CREATING_TOWNS);
            Town town = new Town();
            town.setName(INIT_TOWN);
            Region region = regionService.findByName(RegionServiceImpl.INIT_REGION);
            town.setRegion(region);

            townRepository.save(town);
        }
    }

    @Override
    public TownServiceModel save(TownAddServiceModel townAddServiceModel) {
        LOGGER.info(Messages.ADDING_TOWN);
        Town town = modelMapper.map(townAddServiceModel, Town.class);
        town.setRegion(regionService.getRegionByName(townAddServiceModel.getRegion()));

        return modelMapper.map(townRepository.save(town), TownServiceModel.class);
    }

    @Override
    public List<TownRestServiceModel> getAllByRegionRest(String region) {
        return townRepository.findAllByRegionNameLike(region)
                .stream()
                .map(town -> modelMapper.map(town, TownRestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Town findByName(String town) {
        return townRepository.findByName(town);
    }

    @Override
    public Town getTownByName(String town) {
        return townRepository.findByName(town);
    }
}
