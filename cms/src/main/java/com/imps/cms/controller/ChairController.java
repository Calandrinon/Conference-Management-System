package com.imps.cms.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.imps.cms.model.*;
import com.imps.cms.model.converter.InvitationConverter;
import com.imps.cms.model.converter.UserRoleConverter;
import com.imps.cms.model.dto.*;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chair")
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

    @GetMapping(value = "/add-chair/{conferenceId}/{userId}/{token}")
    public ResponseEntity<UserRoleDto> activateAccount(@PathVariable Long userId, @PathVariable Long conferenceId, @PathVariable String token){
        for(Invitation invitation: invitationService.findByReceiver(userId)){
            if(invitation.getToken().equals(token) && invitation.getUserType() == UserType.CHAIR && invitation.getStatus().equals("PENDING")){
                UserRole userRole = userRoleService.findByConferenceIdAndUserId(conferenceId, userId).get(0);
                userRole.setIsChair(true);
                invitation.setStatus("ACCEPTED");
                invitationService.updateInvitation(invitation);
                return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.updateUserRole(userRole)), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new UserRoleDto(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-chair-invitations/{conferenceId}/{userId}")
    public ResponseEntity<InvitationDto> getChairInvitations(@PathVariable Long conferenceId, @PathVariable Long userId){
        Invitation invitation = invitationService.getChairInvitations(conferenceId, userId);
        if(invitation == null)
            return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(InvitationConverter.convertToDto(invitation), HttpStatus.OK);
    }

    @PostMapping("/invite-chair")
    public ResponseEntity<InvitationDto> inviteChair(@Valid @RequestBody InvitationDto invitationDto) throws URISyntaxException {
        Invitation invitation = Invitation.builder()
                .receiver(userService.findById(invitationDto.getReceiverId()))
                .sender(userService.findById(invitationDto.getSenderId()))
                .conference(conferenceService.findById(invitationDto.getConferenceId()))
                .userType(UserType.CHAIR)
                .text("Wannabe a chair " + invitationDto.getConferenceId() + "?")
                .token(RandomString.make(10))
                .status(invitationDto.getStatus())
                .build();

        invitation = invitationService.addInvitation(invitation);
        //invitationService.sendInvitationEmail(invitation);
        return new ResponseEntity<>(InvitationConverter.convertToDto(invitation), HttpStatus.OK);
    }

    @DeleteMapping("/cancel-invite-chair/{conferenceId}/{receiverId}")
    public ResponseEntity<?> cancelChairInvite(@PathVariable Long conferenceId, @PathVariable Long receiverId){
        invitationService.cancelChairInvitation(conferenceId, receiverId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-pc-member-invitations/{conferenceId}/{userId}")
    public ResponseEntity<InvitationDto> getPcMemberInvitations(@PathVariable Long conferenceId, @PathVariable Long userId){
        Invitation invitation = invitationService.getPcMemberInvitations(conferenceId, userId);
        if(invitation == null)
            return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(InvitationConverter.convertToDto(invitation), HttpStatus.OK);
    }

    @PostMapping("/invite-pc-member")
    public ResponseEntity<InvitationDto> invitePcMember(@Valid @RequestBody InvitationDto invitationDto) throws URISyntaxException {
        Invitation invitation = Invitation.builder()
                .receiver(userService.findById(invitationDto.getReceiverId()))
                .sender(userService.findById(invitationDto.getSenderId()))
                .conference(conferenceService.findById(invitationDto.getConferenceId()))
                .userType(UserType.PC_MEMBER)
                .text("Wannabe a Pc Member for conference " + invitationDto.getConferenceId() + "?")
                .token(RandomString.make(10))
                .status(invitationDto.getStatus())
                .build();

        invitation = invitationService.addInvitation(invitation);
        //invitationService.sendInvitationEmail(invitation);
        return new ResponseEntity<>(InvitationConverter.convertToDto(invitation), HttpStatus.OK);
    }

    @DeleteMapping("/cancel-invite-pc-member/{conferenceId}/{receiverId}")
    public ResponseEntity<?> cancelPcMemberInvite(@PathVariable Long conferenceId, @PathVariable Long receiverId){
        invitationService.cancelPcMemberInvitation(conferenceId, receiverId);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PostMapping("/paper/{sectionId}")
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
