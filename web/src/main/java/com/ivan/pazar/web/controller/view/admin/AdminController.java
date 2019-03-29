package com.ivan.pazar.web.controller.view.admin;

import com.ivan.pazar.persistence.model.service.AdvertismentHomePageServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.view.AdvertisementViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends AdminBaseController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;

    public AdminController(AdvertisementService advertisementService, ModelMapper modelMapper) {
        this.advertisementService = advertisementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/panel")
    public ModelAndView adminView(Model model) {

        return renderView(ViewConstants.VIEWS_ADMIN_PANEL, model);
    }

    @GetMapping("/adds-to-confirm")
    public ModelAndView addsToConfirm(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        PageRequest pageRequest = PageRequest.of(page, ViewConstants.DEFAULT_ELEMENTS_SIZE, Sort.by("addedOn").descending());
        AdvertismentHomePageServiceModel advertisementsPage =
                advertisementService.findNonConfirmedAdvertisements(pageRequest);

        List<AdvertisementViewModel> advertisements = advertisementsPage.getAdvertisementViewServiceModels().stream()
                .map(advertisementViewServiceModel ->
                        modelMapper.map(advertisementViewServiceModel, AdvertisementViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute(ViewConstants.ADVERTS, advertisements);
        model.addAttribute(ViewConstants.PAGE, page);
        model.addAttribute(ViewConstants.TOTAL_PAGES, advertisementsPage.getPages());

        //TODO: REFACTOR
        model.addAttribute(ViewConstants.PAGES, new int[advertisementsPage.getPages()]);

        return renderView(ViewConstants.VIEWS_ADVERTS_HOME, model);
    }
}
