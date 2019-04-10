package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.RegionAddServiceModel;
import com.ivan.pazar.persistence.model.service.RegionServiceModel;
import com.ivan.pazar.persistence.model.service.rest.RegionRestServiceModel;
import com.ivan.pazar.persistence.repository.RegionRepository;
import com.ivan.pazar.persistence.service.service_api.RegionServiceExtended;
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
public class RegionServiceImpl implements RegionServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionServiceImpl.class);

    static final String INIT_REGION = "Sofia";

    private final RegionRepository regionRepository;
    private final ModelMapper modelMapper;

    public RegionServiceImpl(RegionRepository regionRepository, ModelMapper modelMapper) {
        this.regionRepository = regionRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        if (regionRepository.count() == 0) {
            Region region = new Region();
            region.setName(INIT_REGION);
            regionRepository.save(region);
        }
    }

    @Override
    public RegionServiceModel save(RegionAddServiceModel regionAddServiceModel) {
        LOGGER.info(Messages.ADDING_REGION);
        Region region = modelMapper.map(regionAddServiceModel, Region.class);

        return modelMapper.map(regionRepository.save(region), RegionServiceModel.class);
    }

    @Override
    public List<RegionRestServiceModel> getAllRegionsRest() {
        return regionRepository.findAll()
                .stream()
                .map(region -> modelMapper.map(region, RegionRestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RegionServiceModel getRegionByTownName(String townName) {
        return modelMapper.map(regionRepository.findByTownName(townName), RegionServiceModel.class);
    }

    public Region findByName(String name) {
        return regionRepository.findByName(name);
    }

}
