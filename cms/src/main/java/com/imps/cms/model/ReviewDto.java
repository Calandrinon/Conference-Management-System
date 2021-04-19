package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "reviewDto"
        , types = { Review.class }
)
public interface ReviewDto {
    Long getId();
    ProposalDto getProposal();
    UserDto getUser();
    Long getScore();
    String getNotes();
}
