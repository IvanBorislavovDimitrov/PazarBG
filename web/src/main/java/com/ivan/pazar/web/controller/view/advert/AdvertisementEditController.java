package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementPageServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.AdvertisementBindingModel;
import com.ivan.pazar.web.model.view.AdvertisementViewModel;
import com.ivan.pazar.web.pagination.Pagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdvertisementEditController extends AdvertisementBaseController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;
    private final Pagination pagination;

    @Autowired
    public AdvertisementEditController(AdvertisementService advertisementService, ModelMapper modelMapper, Pagination pagination) {
        this.advertisementService = advertisementService;
        this.modelMapper = modelMapper;
        this.pagination = pagination;
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView edit(@RequestParam("advertId") String advertId, Model model) {
        AdvertisementViewModel advertisementViewModel = modelMapper.map(advertisementService.findById(advertId), AdvertisementViewModel.class);
        model.addAttribute(WebConstants.ADVERT, advertisementViewModel);

        return renderView(WebConstants.VIEWS_EDIT_ADVERT, model);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@ModelAttribute(WebConstants.ADVERT) @Valid AdvertisementBindingModel advertisementBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(WebConstants.ID, advertisementBindingModel.getId());
            return renderView(WebConstants.VIEWS_EDIT_ADVERT, model);
        }

        advertisementService.edit(modelMapper.map(advertisementBindingModel, AdvertisementAddServiceModel.class));

        return redirect(String.format(WebConstants.REDIRECT_TO_ADVERT, advertisementBindingModel.getId()));
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
