package com.ivan.pazar.web.pagination;

import com.ivan.pazar.persistence.model.service.AdvertisementHomePageServiceModel;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.view.AdvertisementViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Pagination {

    private final ModelMapper modelMapper;

    @Autowired
    public Pagination(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void createAdvertisementsPages(int page, Model model, AdvertisementHomePageServiceModel advertisementsPage) {
        List<AdvertisementViewModel> advertisements = advertisementsPage.getAdvertisementViewServiceModels().stream()
                .map(advertisementViewServiceModel ->
                        modelMapper.map(advertisementViewServiceModel, AdvertisementViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute(ViewConstants.ADVERTS, advertisements);
        model.addAttribute(ViewConstants.PAGE, page);
        model.addAttribute(ViewConstants.TOTAL_PAGES, advertisementsPage.getPages());
    }

}
