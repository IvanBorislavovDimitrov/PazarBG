package com.ivan.pazar.web.pagination;

import com.ivan.pazar.persistence.model.service.AdvertisementPageServiceModel;
import com.ivan.pazar.web.constants.WebConstants;
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

    public void createAdvertisementsPages(int page, Model model, AdvertisementPageServiceModel advertisementsPage) {
        List<AdvertisementViewModel> advertisements = advertisementsPage.getAdvertisementViewServiceModels().stream()
                .map(advertisementViewServiceModel ->
                        modelMapper.map(advertisementViewServiceModel, AdvertisementViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute(WebConstants.ADVERTS, advertisements);
        model.addAttribute(WebConstants.PAGE, page);
        model.addAttribute(WebConstants.TOTAL_PAGES, advertisementsPage.getPages());
    }

}
