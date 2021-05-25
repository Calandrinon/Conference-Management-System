package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.*;
import com.imps.cms.model.dto.*;
import com.imps.cms.service.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Function;
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
    @Autowired
    private MailService mailService;

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
                .text("Wannabe a chair?")
                .token(RandomString.make(10))
                .status(invitationDto.getStatus())
                .build();

        invitation = invitationService.addInvitation(invitation);
        mailService.sendEmail("Wannabe a chair?", invitation.getText() + " for conference " + invitation.getConference().getTitle() + " \nToken: " + invitation.getToken(), invitation.getReceiver().getEmail());
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
                .text("Wannabe a Pc Member")
                .token(RandomString.make(10))
                .status(invitationDto.getStatus())
                .build();

        invitation = invitationService.addInvitation(invitation);
        mailService.sendEmail("Wannabe a Pc Member?", invitation.getText() + " for conference " + invitation.getConference().getTitle() + " \nToken: " + invitation.getToken(), invitation.getReceiver().getEmail());
        return new ResponseEntity<>(InvitationConverter.convertToDto(invitation), HttpStatus.OK);
    }

    @DeleteMapping("/cancel-invite-pc-member/{conferenceId}/{receiverId}")
    public ResponseEntity<?> cancelPcMemberInvite(@PathVariable Long conferenceId, @PathVariable Long receiverId){
        invitationService.cancelPcMemberInvitation(conferenceId, receiverId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-proposals/{conferenceId}")
    public ResponseEntity<List<ProposalDto>> getProposals(@PathVariable Long conferenceId){
        return new ResponseEntity<>(proposalService.getForConference(conferenceId).stream().map(ProposalConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/get-paper/{paperId}")
    public ResponseEntity<PaperDto> getPaper(@PathVariable Long paperId){
        return new ResponseEntity<>(PaperConverter.convertToDto(paperService.findById(paperId)), HttpStatus.OK);
    }

    @GetMapping("/get-accept-users/{proposalId}")
    public ResponseEntity<List<UserDto>> getAcceptUsers(@PathVariable Long proposalId){
        List<User> users = userService.getAcceptUsers(proposalId);
        return new ResponseEntity<>(users.stream().map(UserConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/get-reject-users/{proposalId}")
    public ResponseEntity<List<UserDto>> getRejectUsers(@PathVariable Long proposalId){
        List<User> users = userService.getRejectUsers(proposalId);
        return new ResponseEntity<>(users.stream().map(UserConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/get-ugh-users/{proposalId}")
    public ResponseEntity<List<UserDto>> getUghUsers(@PathVariable Long proposalId){
        List<User> users = userService.getUghUsers(proposalId);
        return new ResponseEntity<>(users.stream().map(UserConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/assign-proposal/{proposalId}/{userId}")
    public ResponseEntity<ReviewDto> assignProposal(@PathVariable Long proposalId, @PathVariable Long userId){
        Review review = reviewService.addReview(proposalId, userId);
        return new ResponseEntity<>(ReviewConverter.convertToDto(review), HttpStatus.OK);
    }

    @GetMapping("/get-reviews/{proposalId}")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable Long proposalId){
        List<Review> reviews = null;
        try{
            reviews = reviewService.findByProposal(proposalId);
            return new ResponseEntity<>(reviews.stream().map(ReviewConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/manual-review")
    public ResponseEntity<ProposalDto> manualReview(@Valid @RequestBody ProposalDto proposalDto){
        Proposal proposal = new Proposal();
        proposal.setStatus(proposalDto.getStatus());
        proposal.setId(proposalDto.getId());
        proposal.setPaper(paperService.findById(proposalDto.getPaperId()));

        proposal = proposalService.updateProposal(proposal);
        reviewService.resolveReviews(proposal);

        return new ResponseEntity<>(ProposalConverter.convertToDto(proposal), HttpStatus.OK);
    }

    @PostMapping("/auto-review")
    public ResponseEntity<ProposalDto> autoReview(@Valid @RequestBody ProposalDto proposalDto){
        Proposal proposal = new Proposal();
        proposal.setStatus(proposalDto.getStatus());
        proposal.setId(proposalDto.getId());
        proposal.setPaper(paperService.findById(proposalDto.getPaperId()));

        proposal = proposalService.reviewProposal(proposal);

        if(!proposal.getStatus().equals("CONTRADICTORY")){
            reviewService.resolveReviews(proposal);
            mailService.sendEmail("Your paper was " + proposal.getStatus().toLowerCase(),
                    "Your paper " + proposal.getPaper().getTitle() + " was" + proposal.getStatus().toLowerCase(),
                    proposal.getPaper().getAuthor().getEmail());
        }
        return new ResponseEntity<>(ProposalConverter.convertToDto(proposal), HttpStatus.OK);
    }

    @PostMapping("/proposal/acceptOrReject")
    public ResponseEntity<Boolean> acceptOrRejectProposal(@RequestBody ProposalDto proposalDto)
    {
        Proposal proposal = Proposal.builder()
                .paper(paperService.findById(proposalDto.getPaperId()))
                .status(proposalDto.getStatus())
                .build();
        List<Long> scores = reviewService.findByProposal(proposal.getId()).stream()
                .filter(p -> p.getReviewStatus() == ReviewStatus.RESOLVED)
                .map(Review::getScore)
                .collect(Collectors.toList());
        Double scoreStandardDeviation = ReviewService.standardDeviation(scores);
        if(scoreStandardDeviation >= 3){
            List<Review> reviews = reviewService.findByProposal(proposal.getId());
            reviews.forEach(p -> {p.setReviewStatus(ReviewStatus.RESOLVED); reviewService.updateReview(p);});
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
                    .sectionId(null)
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
            reviewService.findByProposal(proposalId)
                    .stream()
                    .map(reviewBuilder)
                    .collect(Collectors.toList())
        );
    }

    @PostMapping("/update-paper")
    public ResponseEntity<Boolean> updatePaper(@RequestBody PaperDto paperDto) throws IOException {
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


    @PostMapping("/update-deadline")
    public ResponseEntity<Conference> updateDeadline(@RequestBody ConferenceDto conferenceDto){
        Conference conference = Conference.builder()
                .id(conferenceDto.getId())
                .title(conferenceDto.getTitle())
                .submitProposal(conferenceDto.getSubmitProposal())
                .bidProposals(conferenceDto.getBidProposals())
                .assignPapersToReviewers(conferenceDto.getAssignPapersToReviewers())
                .reviewPapers(conferenceDto.getReviewPapers())
                .acceptPapers(conferenceDto.getAcceptPapers())
                .createSections(conferenceDto.getCreateSections())
                .assignPapersToSections(conferenceDto.getAssignPapersToSections())
                .listenerSectionRegistration(conferenceDto.getListenerSectionRegistration())
                .build();
        conferenceService.updateConference(conference);
        return ResponseEntity.ok(conference);
    }
}
