package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.UserRoleConverter;
import com.imps.cms.model.dto.PaperDto;
import com.imps.cms.model.dto.ProposalDto;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add-author/{conferenceId}/{userId}")
    public ResponseEntity<UserRoleDto> addAuthor(@PathVariable Long conferenceId, @PathVariable Long userId) throws URISyntaxException {
        UserRole userRole = userRoleService.findByConferenceIdAndUserId(conferenceId, userId).get(0);
        userRole.setIsAuthor(true);
        return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.updateUserRole(userRole)), HttpStatus.OK);
    }

    @PostMapping("/paper")
    public ResponseEntity<Paper> addPaper(@Valid @RequestBody PaperDto paperDto) throws URISyntaxException {
        Paper paper = Paper.builder()
                .title(paperDto.getTitle())
                .subject(paperDto.getSubject())
                .keywords(paperDto.getKeywords())
                .topics(paperDto.getTopics())
                .author(userService.findById(paperDto.getAuthorId()))
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
