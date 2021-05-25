package com.imps.cms.repository;

import com.imps.cms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(excerptProjection = BidInterface.class)
public interface BidRepository extends JpaRepository<Bid, Long> {
    Bid findByProposalAndUser(Proposal proposal, User user);

    List<Bid> findByProposalAndBidStatus(Proposal proposal, BidStatus bidStatus);
}
