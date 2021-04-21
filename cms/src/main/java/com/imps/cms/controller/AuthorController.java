package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.dto.PaperDto;
import com.imps.cms.model.dto.ProposalDto;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private PaperService paperService;

    @PostMapping("/userRole")
    public ResponseEntity<UserRole> addAuthor(@Valid @RequestBody UserRoleDto userRoleDto) throws URISyntaxException {
        UserRole userRole = UserRole.builder()
                .user(userService.findById(userRoleDto.getUserId()))
                .conference(conferenceService.findById(userRoleDto.getConferenceId()))
                .build();

        this.userRoleService.addUserRole(userRole);
        return ResponseEntity.created(new URI("/api/userRole/" + userRole.getId())).body(userRole);
    }

    @PostMapping("/paper")
    public ResponseEntity<Paper> addPaper(@Valid @RequestBody PaperDto paperDto) throws URISyntaxException {
        Paper paper = Paper.builder()
                .title(paperDto.getTitle())
                .subject(paperDto.getSubject())
                .keywords(paperDto.getKeywords())
                .topics(paperDto.getTopics())
                .author(userService.findById(paperDto.getUserId()))
                .filename(paperDto.getFileName())
                .build();

        paperService.addPaper(paper);
        return ResponseEntity.created(new URI("/api/paper/" + paper.getID())).body(paper);
    }

    @PostMapping("/proposal")
    public ResponseEntity<Proposal> addProposal(@Valid @RequestBody ProposalDto proposalDto) throws URISyntaxException {
        Proposal proposal = Proposal.builder()
                .paper(paperService.findById(proposalDto.getPaperId()))
                .status("PENDING")
                .build();

        proposalService.addProposal(proposal);
        return ResponseEntity.created(new URI("/api/proposal/" + proposal.getId())).body(proposal);
    }
}
