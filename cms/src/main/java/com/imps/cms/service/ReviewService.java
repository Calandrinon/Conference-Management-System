package com.imps.cms.service;

import com.imps.cms.model.*;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.ProposalRepository;
import com.imps.cms.repository.ReviewRepository;
import com.imps.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    public Review addReview(Long proposalId, Long userId){
        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow(() -> new RuntimeException("No proposal with this id"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No user with this id"));
        Review review = new Review();
        review.setProposal(proposal);
        review.setUser(user);
        review.setReviewStatus(ReviewStatus.PENDING_FOR_USER);
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review){
        return reviewRepository.save(review);
    }

    public List<Review> findByProposal(Long proposalID){
        Proposal proposal = proposalRepository.findById(proposalID).orElseThrow(() -> new RuntimeException("No proposal with this id"));
        return reviewRepository.findByProposalAndReviewStatusNot(proposal, ReviewStatus.RESOLVED);
    }

    public static Double mean(List<Long> scores){
        return scores.stream().mapToLong(Long::longValue).average().orElse(Double.NaN);
    }

    public static Double standardDeviation(List<Long> scores) {
        double meanScore = mean(scores);
        return Math.sqrt(scores.stream().mapToDouble(Long::doubleValue).reduce((a, b) -> Math.pow(a - meanScore, 2) + Math.pow(b - meanScore, 2)).orElse(Double.NaN));
    }

    public List<Review> getForUser(Long userId, Long conferenceId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No user with this id"));
        Conference conference = conferenceRepository.findById(conferenceId).orElseThrow(() -> new RuntimeException("No conference with thid id"));
        return reviewRepository.findByUser(user).stream()
                .filter(review -> review.getProposal().getPaper().getConference().equals(conference))
                .filter(review -> review.getReviewStatus() != ReviewStatus.RESOLVED)
                .collect(Collectors.toList());
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("no review with this id"));
    }

    public void resolveReviews(Proposal proposal) {
        for(Review review: reviewRepository.findByProposal(proposal)){
            review.setReviewStatus(ReviewStatus.RESOLVED);
            reviewRepository.save(review);
        }
    }
}
