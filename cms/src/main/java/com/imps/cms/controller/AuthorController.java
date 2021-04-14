package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api")
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

    @PostMapping("/userRole")
    public ResponseEntity<UserRole> addAuthor(@Valid @RequestBody UserRole userRole) throws URISyntaxException {
        // long userID, long conferenceID
        /*
        UserRole userRole = UserRole.builder()
                .user(userRepository.getOne(userID))
                .conference(conferenceRepository.getOne(conferenceID))
                .userType(UserType.AUTHOR)
                .build();

         */
        // UserRole userRole = new UserRole(userRepository.getOne(userID), conferenceRepository.getOne(conferenceID), UserType.AUTHOR);
        this.userRoleRepository.save(userRole);
        return ResponseEntity.created(new URI("/api/userRole/" + userRole.getId())).body(userRole);
    }

    @PostMapping("/paper")
    public ResponseEntity<Paper> addPaper(@Valid @RequestBody Paper paper) throws URISyntaxException {
        // String title, String subject, String keywords, String topics, Long authorID, String filename
        /*
        Paper paper = Paper.builder()
                .title(title)
                .subject(subject)
                .keywords(keywords)
                .topics(topics)
                .author(userRepository.getOne(authorID))
                .filename(filename)
                .build();

         */
        // Paper paper = new Paper(title, subject, keywords, topics, userRepository.getOne(authorID), filename);
        paperRepository.save(paper);
        return ResponseEntity.created(new URI("/api/paper/" + paper.getID())).body(paper);
    }

    @PostMapping("/proposal")
    public ResponseEntity<Proposal> addProposal(@Valid @RequestBody Proposal proposal) throws URISyntaxException {
        // long paperId
        /*
        Proposal proposal = Proposal.builder()
                .paper(paperRepository.getOne(paperId))
                .status("Unknown")
                .build();

         */
        // Proposal proposal = new Proposal(paperRepository.getOne(paperId), "Unknown");
        proposalRepository.save(proposal);
        return ResponseEntity.created(new URI("/api/proposal/" + proposal.getId())).body(proposal);
    }
}
