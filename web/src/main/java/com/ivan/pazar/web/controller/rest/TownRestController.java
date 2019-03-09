package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.persistence.dto.view.rest.TownRestViewModel;
import com.ivan.pazar.persistence.service.api.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/towns")
public class TownRestController {

    private final TownService townService;
    private final ModelMapper modelMapper;

    public TownRestController(TownService townService, ModelMapper modelMapper) {
        this.townService = townService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/all", produces = "application/json")
    public List<TownRestViewModel> getTownsByRegion(@RequestParam(name = "region", defaultValue = "") String region) {
        return townService.getAllByRegionRest(region)
                .stream()
                .map(townRestServiceModel -> modelMapper.map(townRestServiceModel, TownRestViewModel.class))
                .sorted()
                .collect(Collectors.toList());
    }
}
