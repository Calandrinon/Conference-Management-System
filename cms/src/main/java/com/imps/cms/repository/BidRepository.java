package com.imps.cms.repository;

import com.imps.cms.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
