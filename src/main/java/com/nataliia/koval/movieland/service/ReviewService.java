package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.entity.Review;
import com.nataliia.koval.movieland.web.controller.entity.ReviewRequest;

public interface ReviewService {
    Review addReview(ReviewRequest reviewRequest, String authorizationHeader);
}
