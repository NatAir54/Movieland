package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.dto.ReviewDto;
import com.nataliia.koval.movieland.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toReviewDto(Review review);
}
