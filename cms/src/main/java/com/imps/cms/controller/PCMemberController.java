package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.UserRoleConverter;
import com.imps.cms.model.dto.BidDto;
import com.imps.cms.model.dto.ProposalDto;
import com.imps.cms.model.dto.ReviewDto;
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
import java.util.List;

@RestController
@RequestMapping("/api")
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
    private BidService bidService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/add-pc-member/{conferenceId}/{userId}/{token}")
    public ResponseEntity<UserRoleDto> activateAccount(@PathVariable Long userId, @PathVariable Long conferenceId, @PathVariable String token){
        for(Invitation invitation: invitationService.findByReceiver(userId)){
            if(invitation.getToken().equals(token) && invitation.getUserType() == UserType.PC_MEMBER){
                UserRole userRole = userRoleService.findByConferenceIdAndUserId(conferenceId, userId).get(0);
                userRole.setIsPcMember(true);
                return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.updateUserRole(userRole)), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new UserRoleDto(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/bids/placeBid")
    public ResponseEntity<Boolean> placeBid(@RequestBody BidDto bidDto){
        Bid bid = Bid.builder()
                .user(userService.findById(bidDto.getUserId()))
                .proposal(proposalService.findById(bidDto.getProposalId()))
                .bidStatus(BidStatus.WAITING)
                .build();
        bidService.addBid(bid);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping("/review/add")
    public ResponseEntity<Boolean> addReview(@RequestBody ReviewDto reviewDto){
        Review review = Review.builder()
                .user(userService.findById(reviewDto.getUserId()))
                .score(reviewDto.getScore())
                .notes(reviewDto.getNotes())
                .proposal(proposalService.findById(reviewDto.getProposalId()))
                .reviewStatus(ReviewStatus.PENDING)
                .build();
        reviewService.addReview(review);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
