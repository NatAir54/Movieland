package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.entity.Review;
import com.nataliia.koval.movieland.entity.User;
import com.nataliia.koval.movieland.repository.ReviewRepository;
import com.nataliia.koval.movieland.service.MovieService;
import com.nataliia.koval.movieland.service.ReviewService;
import com.nataliia.koval.movieland.service.UserService;
import com.nataliia.koval.movieland.service.security.JwtSecurityTokenService;
import com.nataliia.koval.movieland.web.controller.entity.ReviewRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final JwtSecurityTokenService jwtSecurityTokenService;

    @Transactional
    @Override
    public Review addReview(ReviewRequest reviewRequest, String authorizationHeader) {
        String userEmail = jwtSecurityTokenService.extractUsername(authorizationHeader);
        User user = userService.findByEmail(userEmail);

        Movie movie = movieService.findById(reviewRequest.movieId());

        Review review = new Review();
        review.setText(reviewRequest.text());
        review.setUser(user);
        review.setMovieNameRussian(movie.getNameRussian());

        movie.getReviews().add(review);

        return reviewRepository.save(review);
    }
}
