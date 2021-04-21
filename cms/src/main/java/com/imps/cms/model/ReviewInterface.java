package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "reviewDto"
        , types = { Review.class }
)
public interface ReviewInterface {
    Long getId();
    ProposalInterface getProposal();
    UserDtoInterface getUser();
    Long getScore();
    String getNotes();
    ReviewStatus getReviewStatus();
}
