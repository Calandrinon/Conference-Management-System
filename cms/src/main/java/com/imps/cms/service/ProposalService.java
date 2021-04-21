package com.imps.cms.service;

import com.imps.cms.model.Proposal;
import com.imps.cms.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;

    public Proposal findById(Long id){
        return proposalRepository.findById(id).orElseThrow(() -> new RuntimeException("No proposal with this id"));
    }

    public void addProposal(Proposal proposal){
        proposalRepository.save(proposal);
    }

    public void updateProposal(Proposal proposal){
        proposalRepository.save(proposal);
    }
}
