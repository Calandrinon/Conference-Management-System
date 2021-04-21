package com.imps.cms.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.imps.cms.model.*;
import com.imps.cms.model.dto.*;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ChairController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private SectionService sectionService;


    @PostMapping("/invitation/inviteChair")
    public ResponseEntity<Invitation> inviteChairsOrPCMembers(@Valid @RequestBody InvitationDto invitationDto) throws URISyntaxException {
        Invitation invitation = Invitation.builder()
                .receiver(userService.findById(invitationDto.getReceiverId()))
                .sender(userService.findById(invitationDto.getSenderId()))
                .text(invitationDto.getText())
                .token(invitationDto.getToken())
                .userType(invitationDto.getUserType())
                .build();

        invitationService.addInvitation(invitation);
        invitationService.sendInvitationEmail(invitation);
        return ResponseEntity.created(new URI("api/invitation/" + invitation.getId())).body(invitation);
    }

    @GetMapping(value = "/chair/{userId}/{conferenceId}/{token}")
    public ResponseEntity<Boolean> activateAccount(@PathVariable Long userId, @PathVariable Long conferenceId, @PathVariable String token){
        List<Invitation> invitations = invitationService.findByReceiver(userId);
        for(Invitation invitation: invitations){
            if(invitation.getToken().equals(token) && invitation.getUserType() == UserType.CHAIR){
                UserRole userRole = UserRole.builder()
                        .user(userService.findById(userId))
                        .userType(UserType.CHAIR)
                        .conference(conferenceService.findById(conferenceId))
                        .build();
                userRoleService.addUserRole(userRole);
                return ResponseEntity.ok(Boolean.TRUE);
            }
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }

    @PostMapping("/proposal/assign/{userId}")
    public ResponseEntity<Boolean> assignProposal(@RequestBody ProposalDto proposalDto, @PathVariable Long userId){
        Proposal proposal = Proposal.builder()
                .paper(paperService.findById(proposalDto.getPaperId()))
                .status(proposalDto.getStatus())
                .build();
        User user = userService.findById(userId);
        List<Proposal> proposals = user.getProposals();
        proposals.add(proposal);
        user.setProposals(proposals);
        userService.updateUser(user);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping("/review/review/{reviewStatus}")
    public ResponseEntity<Boolean> reviewReviews(@RequestBody ReviewDto reviewDto, @PathVariable ReviewStatus reviewStatus){
        Review review = Review.builder()
                .reviewStatus(reviewStatus)
                .notes(reviewDto.getNotes())
                .proposal(proposalService.findById(reviewDto.getProposalId()))
                .score(reviewDto.getScore())
                .user(userService.findById(reviewDto.getUserId()))
                .build();
        reviewService.updateReview(review);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping("/proposal/acceptOrReject")
    public ResponseEntity<Boolean> acceptOrRejectProposal(@RequestBody ProposalDto proposalDto)
    {
        Proposal proposal = Proposal.builder()
                .paper(paperService.findById(proposalDto.getPaperId()))
                .status(proposalDto.getStatus())
                .build();
        List<Long> scores = reviewService.findByProposal(proposal).stream()
                .filter(p -> p.getReviewStatus() == ReviewStatus.ACCEPTED)
                .map(Review::getScore)
                .collect(Collectors.toList());
        Double scoreStandardDeviation = ReviewService.standardDeviation(scores);
        if(scoreStandardDeviation >= 3){
            List<Review> reviews = reviewService.findByProposal(proposal);
            reviews.forEach(p -> {p.setReviewStatus(ReviewStatus.REJECTED); reviewService.updateReview(p);});
            return ResponseEntity.ok(Boolean.FALSE);
        }
        else{
            double meanScore = ReviewService.mean(scores);
            if(meanScore >= 5) {
                proposal.setStatus("ACCEPTED");
                proposalService.updateProposal(proposal);
                return ResponseEntity.ok(Boolean.TRUE);
            }else {
                proposal.setStatus("REJECTED");
                proposalService.updateProposal(proposal);
                return ResponseEntity.ok(Boolean.FALSE);
            }
        }
    }

    @PostMapping("/section")
    public ResponseEntity<Section> addSection(@Valid @RequestBody SectionDto sectionDto) throws URISyntaxException {
        Section section = Section.builder()
                .conference(conferenceService.findById(sectionDto.getConferenceId()))
                .name(sectionDto.getName())
                .supervisor(userService.findById(sectionDto.getSupervisorId()))
                .build();
        sectionService.addSection(section);
        return ResponseEntity.created(new URI("/api/section/" + section.getId())).body(section);
    }

    public ResponseEntity<Boolean> assignPaperToSection(@RequestBody PaperDto paperDto, @PathVariable Long sectionId){
        Paper paper = Paper.builder()
                .section(sectionService.findById(sectionId))
                .author(userService.findById(paperDto.getUserId()))
                .filename(paperDto.getFileName())
                .title(paperDto.getTitle())
                .subject(paperDto.getSubject())
                .keywords(paperDto.getKeywords())
                .topics(paperDto.getTopics())
                .build();
        paperService.updatePaper(paper);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
