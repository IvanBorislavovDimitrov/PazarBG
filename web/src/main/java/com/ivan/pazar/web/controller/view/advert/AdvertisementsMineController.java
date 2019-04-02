package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.persistence.model.service.AdvertisementPageServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.pagination.Pagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdvertisementsMineController extends AdvertisementBaseController {

    private final AdvertisementService advertisementService;
    private final UserConfiguration userConfiguration;
    private final Pagination pagination;

    @Autowired
    public AdvertisementsMineController(AdvertisementService advertisementService, UserConfiguration userConfiguration, Pagination pagination) {
        this.advertisementService = advertisementService;
        this.userConfiguration = userConfiguration;
        this.pagination = pagination;
    }

    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView mineAdvertisements(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        PageRequest pageRequest = PageRequest.of(page, WebConstants.DEFAULT_ELEMENTS_SIZE);
        AdvertisementPageServiceModel advertisementPageServiceModel =
                advertisementService.findAllByUsername(userConfiguration.loggedUserUsername(), pageRequest);
        pagination.createAdvertisementsPages(page, model, advertisementPageServiceModel);
        model.addAttribute(WebConstants.PAGES, new int[advertisementPageServiceModel.getPages()]);

        return renderView(WebConstants.VIEWS_MY_ADVERTS, model) ;
    }
}
