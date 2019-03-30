package com.ivan.pazar.web.controller.view.review;

import com.ivan.pazar.persistence.model.service.ReviewAddServiceModel;
import com.ivan.pazar.persistence.service.api.ReviewService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.ReviewAddViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ReviewAddController extends ReviewBaseController {

    private final UserConfiguration userConfiguration;
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewAddController(UserConfiguration userConfiguration, ReviewService reviewService, ModelMapper modelMapper) {
        this.userConfiguration = userConfiguration;
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView reviewAdd(Model model) {

        return renderView(WebConstants.VIEWS_REVIEW_ADD, model);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView reviewAddConfirm(@ModelAttribute(WebConstants.REVIEW) @Valid ReviewAddViewModel reviewAddViewModel, @RequestParam("advertId") String advertId, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_REVIEW_ADD, model);
        }

        ReviewAddServiceModel reviewAddServiceModel = modelMapper.map(reviewAddViewModel, ReviewAddServiceModel.class);

        reviewAddServiceModel.setUsername(userConfiguration.loggedUserUsername());
        reviewAddServiceModel.setAdvertisementId(advertId);

        reviewService.save(reviewAddServiceModel);

        return redirect(String.format(WebConstants.REDIRECT_TO_ADVERT, advertId));
    }

    @ModelAttribute(WebConstants.REDIRECT_CATEGORY_ADD)
    public ReviewAddViewModel reviewAddViewModel() {
        return new ReviewAddViewModel();
    }
}
