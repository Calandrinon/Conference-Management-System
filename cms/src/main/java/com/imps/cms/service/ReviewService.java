package com.imps.cms.service;

import com.imps.cms.model.Proposal;
import com.imps.cms.model.Review;
import com.imps.cms.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public void addReview(Review review){
        reviewRepository.save(review);
    }

    public void updateReview(Review review){
        reviewRepository.save(review);
    }

    public List<Review> findByProposal(Proposal proposal){
        return reviewRepository.findByProposal(proposal);
    }

    public static Double mean(List<Long> scores){
        return scores.stream().mapToLong(Long::longValue).average().orElse(Double.NaN);
    }

    public static Double standardDeviation(List<Long> scores) {
        double meanScore = mean(scores);
        return Math.sqrt(scores.stream().mapToDouble(Long::doubleValue).reduce((a, b) -> Math.pow(a - meanScore, 2) + Math.pow(b - meanScore, 2)).orElse(Double.NaN));
    }
}
