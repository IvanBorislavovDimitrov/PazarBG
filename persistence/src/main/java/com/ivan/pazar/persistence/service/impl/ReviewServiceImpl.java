package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Review;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.ReviewAddServiceModel;
import com.ivan.pazar.persistence.model.service.ReviewRestServiceModel;
import com.ivan.pazar.persistence.model.service.ReviewServiceModel;
import com.ivan.pazar.persistence.repository.ReviewRepository;
import com.ivan.pazar.persistence.service.service_api.AdvertisementServiceExtended;
import com.ivan.pazar.persistence.service.service_api.ReviewServiceExtended;
import com.ivan.pazar.persistence.service.service_api.UserServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

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
        LOGGER.info(Messages.ADDING_REVIEW);
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
        LOGGER.info(Messages.GETTING_REVIEWS_BY_ADVERTISEMENT_ID);
        return reviewRepository.findAllByAdvertisementId(advertisementId).stream().map(review -> {
            ReviewRestServiceModel reviewRestServiceModel = new ReviewRestServiceModel();
            reviewRestServiceModel.setId(review.getId());
            reviewRestServiceModel.setAddedOn(review.getAddedOn().toString());
            reviewRestServiceModel.setText(review.getText());
            reviewRestServiceModel.setUsername(review.getUser().getUsername());

            return reviewRestServiceModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public ReviewServiceModel findById(String id) {
        LOGGER.info(Messages.FINDING_REVIEW_BY_ID);
        return modelMapper.map(reviewRepository.findById(id).orElse(null), ReviewServiceModel.class);
    }

    @Override
    public String update(ReviewServiceModel reviewServiceModel) {
        LOGGER.info(Messages.UPDATING_REVIEW);
        Review review = reviewRepository.findById(reviewServiceModel.getId()).orElse(null);
        review.setText(reviewServiceModel.getText());
        String advertId = review.getAdvertisement().getId();
        reviewRepository.save(review);

        return advertId;
    }
}
