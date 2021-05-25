package com.imps.cms.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.imps.cms.model.*;
import com.imps.cms.model.converter.UserRoleConverter;
import com.imps.cms.model.dto.*;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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
                .userType(invitationDto.getUserType())
                .build();

        invitationService.addInvitation(invitation);
        invitationService.sendInvitationEmail(invitation);
        return ResponseEntity.created(new URI("api/invitation/" + invitation.getId())).body(invitation);
    }

    @GetMapping(value = "/add-chair/{conferenceId}/{userId}/{token}")
    public ResponseEntity<UserRoleDto> activateAccount(@PathVariable Long userId, @PathVariable Long conferenceId, @PathVariable String token){
        for(Invitation invitation: invitationService.findByReceiver(userId)){
            if(invitation.getToken().equals(token) && invitation.getUserType() == UserType.CHAIR){
                UserRole userRole = userRoleService.findByConferenceIdAndUserId(conferenceId, userId).get(0);
                userRole.setIsChair(true);
                return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.updateUserRole(userRole)), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new UserRoleDto(), HttpStatus.BAD_REQUEST);
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

    @RequestMapping("/files/{id}")
    public ResponseEntity<byte[]> getFiles(@PathVariable Long id) {
        return new ResponseEntity<>(
                this.paperService.findById(id).getData()
                , HttpStatus.OK
        );
    }

    private final Function<Paper, PaperServerToWebDto> paperBuilder =
            paper -> PaperServerToWebDto
                    .builder()
                    .id(paper.getId())
                    .keywords(paper.getKeywords())
                    .subject(paper.getSubject())
                    .title(paper.getTitle())
                    .sectionId(paper.getSection().getId())
                    .userId(paper.getAuthor().getId())
                    .topics(paper.getTopics())
                    .status(this.proposalService.getProposalByPaper(paper).getStatus())
                    .proposalId(this.proposalService.getProposalByPaper(paper).getId())
                    .build();

    private final Function<Review, ReviewDto> reviewBuilder =
            review -> ReviewDto
                    .builder()
                    .id(review.getId())
                    .reviewStatus(review.getReviewStatus())
                    .notes(review.getNotes())
                    .score(review.getScore())
                    .proposalId(review.getProposal().getId())
                    .userId(review.getUser().getId())
                    .build();

    @RequestMapping("/webPapers/{userId}")
    public ResponseEntity<List<PaperServerToWebDto>> getPapers(@PathVariable Long userId){
        List<PaperServerToWebDto> e =
                this.paperService.findAll()
                .stream()
                .filter(paper -> paper.getAuthor().getId().equals(userId))
                .map(paperBuilder)
                .collect(Collectors.toList());
        System.out.println(e);
        return new ResponseEntity<>(
                this.paperService.findAll()
                        .stream()
                        .filter(paper -> paper.getAuthor().getId().equals(userId))
                        .map(paperBuilder)
                        .collect(Collectors.toList())
                , HttpStatus.OK);
    }


    @RequestMapping("/sections")
    public ResponseEntity<List<SectionDto>> getConferences() {
        return new ResponseEntity<>(
                sectionService.findAll()
                        .stream()
                        .map(section -> SectionDto.builder()
                                .id(section.getId())
                                .name(section.getName())
                                .conferenceId(section.getConference().getId())
                                .supervisorId(section.getSupervisor().getId())
                                .build())
                        .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("/reviewsForProposal/{proposalId}")
    public ResponseEntity<List<ReviewDto>> getReviewForProposal(@PathVariable Long proposalId){
        return ResponseEntity.ok(
            reviewService.findByProposal(proposalService.findById(proposalId))
                    .stream()
                    .map(reviewBuilder)
                    .collect(Collectors.toList())
        );
    }

    @PostMapping("/paper")
    public ResponseEntity<Boolean> assignPaperToSection(@RequestBody PaperDto paperDto) throws IOException {
        Paper paper = Paper.builder()
                .title(paperDto.getTitle())
                .section(this.sectionService.findById(paperDto.getId()))
                .author(this.userService.findById(paperDto.getId()))
                .data(paperDto.getData().getBytes())
                .topics(paperDto.getTopics())
                .keywords(paperDto.getKeywords())
                .subject(paperDto.getSubject())
                .filename(paperDto.getData().getOriginalFilename())
                .build();
        paper.setId(paperDto.getId());
        paperService.updatePaper(paper);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
