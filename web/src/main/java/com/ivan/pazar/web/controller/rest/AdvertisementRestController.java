package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.model.view.rest.AdvertisementRestViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/adverts")
public class AdvertisementRestController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdvertisementRestController(AdvertisementService advertisementService, ModelMapper modelMapper) {
        this.advertisementService = advertisementService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/best-recent", method = RequestMethod.GET)
    public List<AdvertisementRestViewModel> bestRecent() {
            return advertisementService.findSixMostRecentAdvertisements().stream()
                .map(advertisementRestServiceModel ->
                        modelMapper.map(advertisementRestServiceModel, AdvertisementRestViewModel.class))
                .collect(Collectors.toList());
    }
}
