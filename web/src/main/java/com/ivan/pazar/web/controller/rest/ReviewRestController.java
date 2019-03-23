package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.persistence.service.api.ReviewService;
import com.ivan.pazar.web.model.view.rest.ReviewRestViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    public ReviewRestController(ReviewService reviewService, ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping("/all")
    public List<ReviewRestViewModel> getAllReviewsByAdvertisementId(@RequestParam("advertId") String advertId) {
        return reviewService.getReviewsByAdvertisementId(advertId).stream()
                .map(reviewRestServiceModel -> modelMapper.map(reviewRestServiceModel, ReviewRestViewModel.class))
                .collect(Collectors.toList());
    }
}
