package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.ReviewAddServiceModel;
import com.ivan.pazar.persistence.model.service.ReviewRestServiceModel;
import com.ivan.pazar.persistence.model.service.ReviewServiceModel;

import java.util.List;

public interface ReviewService {

    ReviewServiceModel save(ReviewAddServiceModel reviewAddServiceModel);

    List<ReviewRestServiceModel> getReviewsByAdvertisementId(String advertisementId);

    void deleteById(String reviewId);

}
