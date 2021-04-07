package com.imps.cms.controller;

import com.imps.cms.model.Paper;
import com.imps.cms.model.Proposal;
import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import com.imps.cms.repository.PaperRepository;
import com.imps.cms.repository.ProposalRepository;
import com.imps.cms.repository.UserRepository;
import org.springframework.stereotype.Controller;

@Controller
public class AuthorController {
    private final UserRepository userRepository;
    private final ProposalRepository proposalRepository;
    private final PaperRepository paperRepository;

    public AuthorController(UserRepository userRepository, ProposalRepository proposalRepository, PaperRepository paperRepository) {
        this.userRepository = userRepository;
        this.proposalRepository = proposalRepository;
        this.paperRepository = paperRepository;
    }

    public void addAuthor(String name, String salt, String email, String password){
        User user = new User(name, UserType.AUTHOR, salt, email, password);
        userRepository.save(user);
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
