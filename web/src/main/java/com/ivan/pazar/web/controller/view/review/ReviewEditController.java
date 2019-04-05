package com.ivan.pazar.web.controller.view.review;

import com.ivan.pazar.persistence.model.service.ReviewServiceModel;
import com.ivan.pazar.persistence.service.api.ReviewService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.view.ReviewViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReviewEditController extends ReviewBaseController {

    private final ReviewService reviewService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;

    @Autowired
    public ReviewEditController(ReviewService reviewService, ModelMapper modelMapper, UserConfiguration userConfiguration) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editReview(@RequestParam("reviewId") String reviewId, Model model) {
        ReviewServiceModel reviewServiceModel = reviewService.findById(reviewId);
        if (!reviewServiceModel.getUser().getUsername().equals(userConfiguration.loggedUserUsername())) {
            // TODO: Implement forbidden logic
        }
        ReviewViewModel reviewViewModel = modelMapper.map(reviewServiceModel, ReviewViewModel.class);

        model.addAttribute(WebConstants.REVIEW, reviewViewModel);

        model.addAttribute(WebConstants.ID, reviewId);

        return renderView(WebConstants.VIEWS_EDIT_REVIEW, model);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editReviewConfirm(ReviewViewModel reviewViewModel) {
        String advertId = reviewService.update(modelMapper.map(reviewViewModel, ReviewServiceModel.class));

        return redirect(String.format(WebConstants.REDIRECT_TO_ADVERT, advertId));
    }
}
