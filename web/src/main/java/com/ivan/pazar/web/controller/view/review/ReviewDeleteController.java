package com.ivan.pazar.web.controller.view.review;

import com.ivan.pazar.persistence.service.api.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewDeleteController extends ReviewBaseController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewDeleteController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteConfirm(@RequestParam("reviewId") String reviewId) {
        reviewService.deleteById(reviewId);
        return new ResponseEntity<>(HttpStatus.TEMPORARY_REDIRECT);
    }
}
