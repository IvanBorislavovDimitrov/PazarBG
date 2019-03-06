package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.domain.model.dto.view.rest.RegionRestViewModel;
import com.ivan.pazar.persistence.service.api.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/regions")
public class RegionRestController {

    private final RegionService regionService;
    private final ModelMapper modelMapper;

    @Autowired
    public RegionRestController(RegionService regionService, ModelMapper modelMapper) {
        this.regionService = regionService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/all", produces = "application/json")
    public List<RegionRestViewModel> getAllRegions() {
        return regionService.getAllRegionsRest()
                .stream()
                .map(regionRestServiceViewModel -> modelMapper.map(regionRestServiceViewModel, RegionRestViewModel.class))
                .collect(Collectors.toList());
    }
}
