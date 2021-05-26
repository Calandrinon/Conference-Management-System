package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.UserRoleConverter;
import com.imps.cms.model.dto.ProposalDto;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


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
    @Autowired
    private SectionService sectionService;

    @GetMapping("/add-author/{conferenceId}/{userId}")
    public ResponseEntity<UserRoleDto> addAuthor(@PathVariable Long conferenceId, @PathVariable Long userId) throws URISyntaxException {
        UserRole userRole = userRoleService.findByConferenceIdAndUserId(conferenceId, userId);
        userRole.setIsAuthor(true);
        return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.updateUserRole(userRole)), HttpStatus.OK);
    }

    @PostMapping("/file/{title}/{subject}/{keywords}/{topics}/{userId}/{conferenceId}")
    public ResponseEntity<Long> addPaper(
            @Valid @RequestBody MultipartFile file
            , @PathVariable String title
            , @PathVariable String subject
            , @PathVariable String keywords
            , @PathVariable String topics
            , @PathVariable Long userId
            , @PathVariable Long conferenceId) throws IOException {

        return ResponseEntity.ok(
            paperService.addFile(
                    file
                    , title
                    , subject
                    , keywords
                    , topics
                    , userService.findById(userId)
                    , conferenceService.findById(conferenceId)
            )
        );
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
