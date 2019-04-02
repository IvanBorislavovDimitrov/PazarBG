package com.ivan.pazar.web.controller.view.admin;

import com.ivan.pazar.persistence.model.service.AdvertisementPageServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.pagination.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminAdvertisementsController extends AdminBaseController {

    private final AdvertisementService advertisementService;
    private final Pagination pagination;

    public AdminAdvertisementsController(AdvertisementService advertisementService, Pagination pagination) {
        this.advertisementService = advertisementService;
        this.pagination = pagination;
    }

    @GetMapping("/adds-to-confirm")
    public ModelAndView addsToConfirm(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        PageRequest pageRequest = PageRequest.of(page, WebConstants.DEFAULT_ELEMENTS_SIZE, Sort.by(WebConstants.ADDED_ON).descending());
        AdvertisementPageServiceModel advertisementsPage =
                advertisementService.findNonConfirmedAdvertisements(pageRequest);

        pagination.createAdvertisementsPages(page, model, advertisementsPage);
        //TODO: REFACTOR
        model.addAttribute(WebConstants.PAGES, new int[advertisementsPage.getPages()]);

        return renderView(WebConstants.VIEWS_ADVERTS_HOME, model);
    }

    @PostMapping("/confirm-add")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT', 'MODERATOR')")
    public ModelAndView confirmAdd(@RequestParam("advertId") String advertId) {
        advertisementService.activateAdvertisement(advertId);

        return redirect(String.format(WebConstants.REDIRECT_TO_ADVERT, advertId));
    }
}