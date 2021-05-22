package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.InvitationConverter;
import com.imps.cms.model.dto.*;
import com.imps.cms.service.*;
import net.bytebuddy.utility.RandomString;
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
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private InvitationService invitationService;

    @PostMapping("/add-conference")
    public ResponseEntity<Conference> addConference(@Valid @RequestBody Conference conference) {
        conference = conferenceService.addConference(conference);
        for(User user: userService.getAll()){
            userRoleService.setEmptyUserRole(conference, user);
        }

        return new ResponseEntity<>(conference, HttpStatus.OK);
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
        //invitationService.sendInvitationEmail(invitation);
        return new ResponseEntity<>(InvitationConverter.convertToDto(invitation), HttpStatus.OK);
    }

    @DeleteMapping("/cancel-invite-chair/{conferenceId}/{receiverId}")
    public ResponseEntity<?> cancelChairInvite(@PathVariable Long conferenceId, @PathVariable Long receiverId){
        invitationService.cancelChairInvitation(conferenceId, receiverId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
