package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
