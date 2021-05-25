package com.imps.cms.service;

import com.imps.cms.model.Bid;
import com.imps.cms.model.Proposal;
import com.imps.cms.model.User;
import com.imps.cms.repository.BidRepository;
import com.imps.cms.repository.ProposalRepository;
import com.imps.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Bid> getAll(){
        return bidRepository.findAll();
    }

    public Bid addBid(Bid bid){
        return bidRepository.save(bid);
    }

    public Bid getBidByProposalAndUser(Long proposalId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("no user with this id"));
        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow(() -> new RuntimeException("no proposal with this id"));
        return bidRepository.findByProposalAndUser(proposal, user);
    }
}
