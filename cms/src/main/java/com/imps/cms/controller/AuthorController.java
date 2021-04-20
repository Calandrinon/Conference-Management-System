package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.dto.PaperDto;
import com.imps.cms.model.dto.ProposalDto;
import com.imps.cms.model.dto.UserRoleDto;
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
    public ResponseEntity<UserRole> addAuthor(@Valid @RequestBody UserRoleDto userRoleDto) throws URISyntaxException {
        UserRole userRole = UserRole.builder()
                .user(userRepository.findById(userRoleDto.getUserId()).orElseThrow(() -> new RuntimeException("no user with this id")))
                .conference(conferenceRepository.findById(userRoleDto.getConferenceId()).orElseThrow(() -> new RuntimeException("no conference with this id")))
                .userType(UserType.AUTHOR)
                .build();

        this.userRoleRepository.save(userRole);
        return ResponseEntity.created(new URI("/api/userRole/" + userRole.getId())).body(userRole);
    }

    @PostMapping("/paper")
    public ResponseEntity<Paper> addPaper(@Valid @RequestBody PaperDto paperDto) throws URISyntaxException {
        Paper paper = Paper.builder()
                .title(paperDto.getTitle())
                .subject(paperDto.getSubject())
                .keywords(paperDto.getKeywords())
                .topics(paperDto.getTopics())
                .author(userRepository.findById(paperDto.getUserId()).orElseThrow(() -> new RuntimeException("no user with this id")))
                .filename(paperDto.getFileName())
                .build();

        paperRepository.save(paper);
        return ResponseEntity.created(new URI("/api/paper/" + paper.getID())).body(paper);
    }

    @PostMapping("/proposal")
    public ResponseEntity<Proposal> addProposal(@Valid @RequestBody ProposalDto proposalDto) throws URISyntaxException {
        Proposal proposal = Proposal.builder()
                .paper(paperRepository.findById(proposalDto.getPaperId()).orElseThrow(() -> new RuntimeException("no paper with this id")))
                .status("Unknown")
                .build();

        proposalRepository.save(proposal);
        return ResponseEntity.created(new URI("/api/proposal/" + proposal.getId())).body(proposal);
    }
}
