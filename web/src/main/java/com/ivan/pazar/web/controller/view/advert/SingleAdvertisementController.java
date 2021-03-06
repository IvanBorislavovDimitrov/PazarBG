package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.persistence.service.api.MessageService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.view.AdvertisementViewModel;
import com.ivan.pazar.web.model.view.MessageViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SingleAdvertisementController extends AdvertisementBaseController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;
    private final MessageService messageService;
    private final UserConfiguration userConfiguration;

    @Autowired
    public SingleAdvertisementController(AdvertisementService advertisementService, ModelMapper modelMapper, MessageService messageService, UserConfiguration userConfiguration) {
        this.advertisementService = advertisementService;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
        this.userConfiguration = userConfiguration;
    }

    @GetMapping("/{id}")
    public ModelAndView singleAdvertisement(@PathVariable("id") String id, Model model) {
        AdvertisementViewModel advertisementViewModel = modelMapper.map(advertisementService.findById(id), AdvertisementViewModel.class);

        advertisementService.incrementViews(id);

        model.addAttribute(WebConstants.ADVERT, advertisementViewModel);
        return renderView(WebConstants.VIEWS_SINGLE_ADVERT, model);
    }

    @PostMapping("/send-message")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView sendMessage(@RequestParam("advertId") String advertId, MessageViewModel messageViewModel) {
        messageService.sendMessage(advertId, modelMapper.map(messageViewModel, MessageAddServiceModel.class),
                userConfiguration.loggedUserUsername());

        return redirect(String.format(WebConstants.REDIRECT_TO_ADVERT, advertId));
    }

    @DeleteMapping("/delete")
    public ModelAndView deleteAdvert(@RequestParam("advertId") String advertId) {
        advertisementService.deleteById(advertId);

        return redirect(WebConstants.REDIRECT_INDEX);
    }
}
