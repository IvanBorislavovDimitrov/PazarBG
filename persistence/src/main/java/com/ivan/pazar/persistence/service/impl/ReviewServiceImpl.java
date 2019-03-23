package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Review;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.model.service.ReviewAddServiceModel;
import com.ivan.pazar.persistence.model.service.ReviewRestServiceModel;
import com.ivan.pazar.persistence.model.service.ReviewServiceModel;
import com.ivan.pazar.persistence.repository.ReviewRepository;
import com.ivan.pazar.persistence.service.service_api.AdvertisementServiceExtended;
import com.ivan.pazar.persistence.service.service_api.ReviewServiceExtended;
import com.ivan.pazar.persistence.service.service_api.UserServiceExtended;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewServiceExtended {

    private final ReviewRepository reviewRepository;
    private final AdvertisementServiceExtended advertisementService;
    private final UserServiceExtended userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, AdvertisementServiceExtended advertisementService, UserServiceExtended userService, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.advertisementService = advertisementService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewServiceModel save(ReviewAddServiceModel reviewAddServiceModel) {
        Review review = modelMapper.map(reviewAddServiceModel, Review.class);
        review.setId(null);
        User user = userService.getUserByUsername(reviewAddServiceModel.getUsername());
        Advertisement advertisement = advertisementService.getAdvertisementById(reviewAddServiceModel.getAdvertisementId());

        review.setAdvertisement(advertisement);
        review.setUser(user);
        review.setAddedOn(LocalDateTime.now());

        return modelMapper.map(reviewRepository.save(review), ReviewServiceModel.class);
    }

    @Override
    public List<ReviewRestServiceModel> getReviewsByAdvertisementId(String advertisementId) {
        return reviewRepository.findAllByAdvertisementId(advertisementId).stream().map(review -> {
            ReviewRestServiceModel reviewRestServiceModel = new ReviewRestServiceModel();
            reviewRestServiceModel.setId(review.getId());
            reviewRestServiceModel.setAddedOn(review.getAddedOn().toString());
            reviewRestServiceModel.setText(review.getText());
            reviewRestServiceModel.setUsername(review.getUser().getUsername());

            return reviewRestServiceModel;
        }).collect(Collectors.toList());
    }
}
