package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.entity.Review;
import com.nataliia.koval.movieland.service.ReviewService;
import com.nataliia.koval.movieland.web.controller.entity.ReviewRequest;
import com.nataliia.koval.movieland.web.controller.entity.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@RequestHeader("Authorization") String authorizationHeader,
                                                    @RequestBody ReviewRequest reviewRequest) {

        Review savedReview = reviewService.addReview(reviewRequest, authorizationHeader);
        ReviewResponse reviewResponse = new ReviewResponse(savedReview.getMovieNameRussian(), savedReview.getText());

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);
    }
}
