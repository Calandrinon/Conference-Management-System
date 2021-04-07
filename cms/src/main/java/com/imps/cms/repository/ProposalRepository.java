package com.imps.cms.repository;

import com.imps.cms.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
}
