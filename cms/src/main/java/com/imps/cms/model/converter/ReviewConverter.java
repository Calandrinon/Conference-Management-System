package com.imps.cms.model.converter;

import com.imps.cms.model.Review;
import com.imps.cms.model.dto.ReviewDto;

public class ReviewConverter {
    public static ReviewDto convertToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewStatus(review.getReviewStatus());
        reviewDto.setId(review.getId());
        reviewDto.setNotes(review.getNotes());
        reviewDto.setScore(review.getScore());
        reviewDto.setProposalId(review.getProposal().getId());
        reviewDto.setUserId(review.getUser().getId());
        return reviewDto;
    }
}
