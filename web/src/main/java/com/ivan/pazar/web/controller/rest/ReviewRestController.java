package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.persistence.service.api.ReviewService;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.view.rest.ReviewRestViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/reviews")
public class ReviewRestController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;

    public ReviewRestController(UserService userService, ReviewService reviewService, ModelMapper modelMapper, UserConfiguration userConfiguration) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
    }

    @RequestMapping("/all")
    public List<ReviewRestViewModel> getAllReviewsByAdvertisementId(@RequestParam("advertId") String advertId) {
        return reviewService.getReviewsByAdvertisementId(advertId).stream()
                .map(reviewRestServiceModel -> {
                    ReviewRestViewModel reviewRestViewModel = modelMapper.map(reviewRestServiceModel, ReviewRestViewModel.class);
                    String loggedUserUsername = userConfiguration.loggedUserUsername();
                    if (loggedUserUsername != null && !loggedUserUsername.equals(ViewConstants.ANONYMOUS_USER)) {
                        reviewRestViewModel.setLoggedUserUsername(userConfiguration.loggedUserUsername());
                        Set<String> rolesForUser = userService.getRolesForUser(userConfiguration.loggedUserUsername());
                        reviewRestViewModel.setLoggedUserRoles(rolesForUser);
                    }

                    return reviewRestViewModel;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public ModelAndView deleteConfirm(@RequestParam("reviewId") String reviewId) {
        reviewService.deleteById(reviewId);

        return new ModelAndView("redirect:/" + ViewConstants.REDIRECT_INDEX);
    }

    
}
