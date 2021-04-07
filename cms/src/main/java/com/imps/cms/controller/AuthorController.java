package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.repository.*;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorController {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ConferenceRepository conferenceRepository;
    private final ProposalRepository proposalRepository;
    private final PaperRepository paperRepository;

    public AuthorController(UserRepository userRepository, UserRoleRepository userRoleRepository, ConferenceRepository conferenceRepository, ProposalRepository proposalRepository, PaperRepository paperRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.conferenceRepository = conferenceRepository;
        this.proposalRepository = proposalRepository;
        this.paperRepository = paperRepository;
    }

    public void addAuthor(long userID, long conferenceID){
        UserRole userRole = new UserRole(userRepository.getOne(userID), conferenceRepository.getOne(conferenceID), UserType.AUTHOR);
        this.userRoleRepository.save(userRole);
    }

    public void addPaper(String title, String subject, String keywords, String topics, Long authorID, String filename){
        Paper paper = new Paper(title, subject, keywords, topics, userRepository.getOne(authorID), filename);
        paperRepository.save(paper);
    }

    public void addProposal(long paperId){
        Proposal proposal = new Proposal(paperRepository.getOne(paperId), "Unknown");
        proposalRepository.save(proposal);
    }
}
