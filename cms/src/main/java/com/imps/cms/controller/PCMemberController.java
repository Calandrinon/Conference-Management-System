package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.*;
import com.imps.cms.model.dto.*;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pc-member")
public class PCMemberController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ProposalService proposalService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private BidService bidService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/add-pc-member/{conferenceId}/{userId}/{token}")
    public ResponseEntity<UserRoleDto> activateAccount(@PathVariable Long userId, @PathVariable Long conferenceId, @PathVariable String token){
        for(Invitation invitation: invitationService.findByReceiver(userId)){
            if(invitation.getToken().equals(token) && invitation.getUserType() == UserType.PC_MEMBER && invitation.getStatus().equals("PENDING")){
                UserRole userRole = userRoleService.findByConferenceIdAndUserId(conferenceId, userId).get(0);
                userRole.setIsPcMember(true);
                invitation.setStatus("ACCEPTED");
                invitationService.updateInvitation(invitation);
                return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.updateUserRole(userRole)), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new UserRoleDto(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-proposals/{conferenceId}")
    public ResponseEntity<List<ProposalDto>> getProposals(@PathVariable Long conferenceId){

        return new ResponseEntity<>(proposalService.getForConference(conferenceId).stream().map(ProposalConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/get-paper/{paperId}")
    public ResponseEntity<PaperDto> getPaper(@PathVariable Long paperId){
        return new ResponseEntity<>(PaperConverter.convertToDto(paperService.findById(paperId)), HttpStatus.OK);
    }

    @PostMapping("/place-bid")
    public ResponseEntity<BidDto> placeBid(@Valid @RequestBody BidDto bidDto){
        Bid bid = new Bid();
        bid.setProposal(proposalService.findById(bidDto.getProposalId()));
        bid.setUser(userService.findById(bidDto.getUserId()));
        bid.setBidStatus(bidDto.getBidStatus());
        return new ResponseEntity<>(BidConverter.convertToDto(bidService.addBid(bid)), HttpStatus.OK);
    }

    @GetMapping("/get-bid/{proposalId}/{userId}")
    public ResponseEntity<BidDto> getBid(@PathVariable Long proposalId, @PathVariable Long userId){
        Bid bid = null;
        try {
            bid = bidService.getBidByProposalAndUser(proposalId, userId);
            return new ResponseEntity<>(BidConverter.convertToDto(bid), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("get-reviews-for-user/{userId}/{conferenceId}")
    public ResponseEntity<List<ReviewDto>> getReviewsForUser(@PathVariable Long userId, @PathVariable Long conferenceId){
        List<Review> reviews = reviewService.getForUser(userId, conferenceId);
        return new ResponseEntity<>(reviews.stream().map(ReviewConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("get-proposal-for-review/{reviewId}")
    public ResponseEntity<ProposalDto> getProposalForReview(@PathVariable Long reviewId){
        Proposal proposal = reviewService.findById(reviewId).getProposal();
        return new ResponseEntity<>(ProposalConverter.convertToDto(proposal), HttpStatus.OK);
    }

    @PutMapping("review-proposal")
    public ResponseEntity<ReviewDto> reviewProposal(@Valid @RequestBody ReviewDto reviewDto){
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setUser(userService.findById(reviewDto.getUserId()));
        review.setProposal(proposalService.findById(reviewDto.getProposalId()));
        review.setNotes(reviewDto.getNotes());
        review.setScore(reviewDto.getScore());
        review.setReviewStatus(ReviewStatus.PENDING_FOR_CHAIR);

        review = reviewService.updateReview(review);

        return new ResponseEntity<>(ReviewConverter.convertToDto(review), HttpStatus.OK);
    }

    @GetMapping("get-submitted-review/{reviewId}")
    public ResponseEntity<Boolean> getSubmittedReview(@PathVariable Long reviewId){
        Review review = reviewService.findById(reviewId);
        return new ResponseEntity<>(review.getReviewStatus() != ReviewStatus.PENDING_FOR_USER, HttpStatus.OK);
    }
}
