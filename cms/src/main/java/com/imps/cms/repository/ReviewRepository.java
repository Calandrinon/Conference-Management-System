package com.imps.cms.repository;

import com.imps.cms.model.Proposal;
import com.imps.cms.model.Review;
import com.imps.cms.model.ReviewInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = ReviewInterface.class)
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProposal(Proposal proposal);
}
