package com.imps.cms.repository;

import com.imps.cms.model.Paper;
import com.imps.cms.model.Proposal;
import com.imps.cms.model.ProposalInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = ProposalInterface.class)
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    Proposal findProposalByPaper(Paper paper);

    List<Proposal> findByPaper(Paper paper);
}
