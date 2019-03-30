package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.view.AdvertisementViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SingleAdvertisementController extends AdvertisementBaseController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;

    @Autowired
    public SingleAdvertisementController(AdvertisementService advertisementService, ModelMapper modelMapper) {
        this.advertisementService = advertisementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ModelAndView singleAdvertisement(@PathVariable("id") String id, Model model) {
        AdvertisementViewModel advertisementViewModel = modelMapper.map(advertisementService.findById(id), AdvertisementViewModel.class);

        model.addAttribute(ViewConstants.ADVERT, advertisementViewModel);
        return renderView(ViewConstants.VIEWS_SINGLE_ADVERT, model);
    }


}
