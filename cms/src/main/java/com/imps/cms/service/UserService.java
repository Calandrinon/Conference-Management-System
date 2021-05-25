package com.imps.cms.service;

import com.imps.cms.model.Bid;
import com.imps.cms.model.BidStatus;
import com.imps.cms.model.Proposal;
import com.imps.cms.model.User;
import com.imps.cms.model.dto.UserDto;
import com.imps.cms.repository.BidRepository;
import com.imps.cms.repository.ProposalRepository;
import com.imps.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private BidRepository bidRepository;

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user with this id"));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getAcceptUsers(Long proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow(() -> new RuntimeException("No proposal with this id"));
        List<Bid> bids = bidRepository.findByProposalAndBidStatus(proposal, BidStatus.ACCEPT);
        return bids.stream()
            .map(Bid::getUser)
            .collect(Collectors.toList());
    }

    public List<User> getRejectUsers(Long proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow(() -> new RuntimeException("No proposal with this id"));
        List<Bid> bids = bidRepository.findByProposalAndBidStatus(proposal, BidStatus.REJECT);
        return bids.stream()
                .map(Bid::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUghUsers(Long proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow(() -> new RuntimeException("No proposal with this id"));
        List<Bid> bids = bidRepository.findByProposalAndBidStatus(proposal, BidStatus.UGH);
        return bids.stream()
                .map(Bid::getUser)
                .collect(Collectors.toList());
    }
}
