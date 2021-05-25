package com.imps.cms.service;

import com.imps.cms.model.Paper;
import com.imps.cms.model.Proposal;
import com.imps.cms.model.User;
import com.imps.cms.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Proposal getProposalByPaper(Paper paper) {
        Proposal prop = this.proposalRepository.findProposalByPaper(paper);
        System.out.println("get proposal by paper : " + prop);
        System.out.println("the paper is : " + paper);
        return prop;
    }

    public void updateProposal(Proposal proposal){
        proposalRepository.save(proposal);
    }
}
