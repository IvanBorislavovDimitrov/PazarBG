package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Review;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.model.service.ReviewAddServiceModel;
import com.ivan.pazar.persistence.model.service.ReviewServiceModel;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.repository.ReviewRepository;
import com.ivan.pazar.persistence.service.api.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AdvertisementServiceImpl advertisementService;

    @Mock
    private UserServiceImpl userService;

    private ReviewServiceImpl reviewService;

    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        reviewService = new ReviewServiceImpl(reviewRepository, advertisementService, userService, modelMapper);
    }

    @Test
    public void reviewService_save_reviewSaved() {
        ReviewAddServiceModel reviewAddServiceModel = mock(ReviewAddServiceModel.class);
        User user = mock(User.class);
        Advertisement advertisement = mock(Advertisement.class);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(advertisementService.getAdvertisementById(anyString())).thenReturn(advertisement);
        Review review = mock(Review.class);
        when(reviewRepository.save(any())).thenReturn(review);
        reviewService.save(reviewAddServiceModel);
        verify(reviewRepository).save(any());
    }

    @Test
    public void reviewService_getReviewsByAdvertisementId_reviewsById() {
        String advertId = "123";
        Review r1 = mock(Review.class);
        when(r1.getAddedOn()).thenReturn(LocalDateTime.now());
        when(r1.getUser()).thenReturn(mock(User.class));
        Review r2 = mock(Review.class);
        when(reviewRepository.findAllByAdvertisementId(advertId)).thenReturn(Arrays.asList(r1, r2));
        when(r2.getAddedOn()).thenReturn(LocalDateTime.now());
        when(r2.getUser()).thenReturn(mock(User.class));

        assertEquals(2, reviewService.getReviewsByAdvertisementId(advertId).size());
    }

    @Test
    public void reviewService_deleteById_reviewIsDeleted() {
        String reviewId = "123";
        reviewService.deleteById(reviewId);
        verify(reviewRepository).deleteById(reviewId);
    }

    @Test
    public void reviewService_findById_reviewFound() {
        Review review = mock(Review.class);
        String id = "123";
        Optional<Review> optionalReview = Optional.of(review);
        when(reviewRepository.findById(id)).thenReturn(optionalReview);
        reviewService.findById(id);
        verify(reviewRepository).findById(id);
    }

    @Test
    public void reviewService_update_reviewUpdated() {
        ReviewServiceModel reviewServiceModel = mock(ReviewServiceModel.class);
        String id = "123";
        when(reviewServiceModel.getId()).thenReturn(id);
        Review review = mock(Review.class);
        Optional<Review> optionalReview = Optional.of(review);
        when(review.getAdvertisement()).thenReturn(mock(Advertisement.class));
        when(reviewRepository.findById(reviewServiceModel.getId())).thenReturn(optionalReview);
        reviewService.update(reviewServiceModel);
        verify(reviewRepository).save(any());
    }
}
