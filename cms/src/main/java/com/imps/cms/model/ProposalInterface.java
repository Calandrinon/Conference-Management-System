package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "proposalDto"
        , types = { Proposal.class }
)
public interface ProposalInterface {
    Long getId();
    PaperInterface getPaper();
    String getStatus();
}
