package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.persistence.model.service.AdvertisementPageServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdvertisementSearchController extends AdvertisementBaseController {

    private final AdvertisementService advertisementService;
    private final Pagination pagination;

    @Autowired
    public AdvertisementSearchController(AdvertisementService advertisementService, Pagination pagination) {
        this.advertisementService = advertisementService;
        this.pagination = pagination;
    }

    @GetMapping("/search")
    public ModelAndView searchForAdvertisements(@RequestParam(value = "keyword", defaultValue = "%%") String keyword, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        PageRequest pageRequest = PageRequest.of(page, WebConstants.DEFAULT_ELEMENTS_SIZE, Sort.by(WebConstants.ADDED_ON).descending());

        AdvertisementPageServiceModel advertisementPageServiceModel = advertisementService.findByKeyword(keyword, pageRequest);

        pagination.createAdvertisementsPages(page, model, advertisementPageServiceModel);

        return renderView(WebConstants.VIEWS_ADVERTS_HOME, model);
    }
}
