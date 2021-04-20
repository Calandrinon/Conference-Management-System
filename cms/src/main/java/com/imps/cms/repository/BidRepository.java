package com.imps.cms.repository;

import com.imps.cms.model.Bid;
import com.imps.cms.model.BidInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(excerptProjection = BidInterface.class)
public interface BidRepository extends JpaRepository<Bid, Long> {
}
