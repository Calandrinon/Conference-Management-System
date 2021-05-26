package com.imps.cms.repository;

import com.imps.cms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = ReviewInterface.class)
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProposal(Proposal proposal);

    List<Review> findByProposalAndReviewStatusNot(Proposal proposal, ReviewStatus reviewStatus);

    List<Review> findByUser(User user);

    List<Review> findByProposalAndReviewStatus(Proposal proposal, ReviewStatus resolved);
}
