package com.imps.cms.service;


import com.imps.cms.model.Conference;
import com.imps.cms.model.Proposal;
import com.imps.cms.model.Review;
import com.imps.cms.model.ReviewStatus;
import com.imps.cms.repository.ConferenceRepository;

import com.imps.cms.model.Paper;
import com.imps.cms.model.Proposal;
import com.imps.cms.model.User;

import com.imps.cms.repository.ProposalRepository;
import com.imps.cms.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Proposal findById(Long id){
        return proposalRepository.findById(id).orElseThrow(() -> new RuntimeException("No proposal with this id"));
    }

    public void addProposal(Proposal proposal){
        proposalRepository.save(proposal);
    }

    public Proposal updateProposal(Proposal proposal){
        return proposalRepository.save(proposal);
    }

    public List<Proposal> getAll() {
        return proposalRepository.findAll();
    }

    public List<Proposal> getForConference(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId).orElseThrow(() -> new RuntimeException("No conference with this id"));
        return proposalRepository.findAll().stream()
                .filter(proposal -> proposal.getPaper().getConference().equals(conference))
                .filter(proposal -> proposal.getStatus().equals("PENDING") || proposal.getStatus().equals("CONTRADICTORY"))
                .collect(Collectors.toList());
    }

    public Proposal reviewProposal(Proposal proposal) {
        List<Long> scores = reviewRepository.findByProposal(proposal).stream()
                .filter(review -> review.getReviewStatus() == ReviewStatus.PENDING_FOR_CHAIR)
                .map(Review::getScore)
                .collect(Collectors.toList());

        if(Collections.max(scores) < 5){
            proposal.setStatus("REJECTED");
            return proposalRepository.save(proposal);
        }
        else if (Collections.min(scores) >= 5){
            proposal.setStatus("ACCEPTED");
            return proposalRepository.save(proposal);
        }
        else {
            proposal.setStatus("CONTRADICTORY");
            return proposalRepository.save(proposal);
        }
    }

    public Proposal getProposalByPaper(Paper paper) {
        return this.proposalRepository.findProposalByPaper(paper);
    }
}
