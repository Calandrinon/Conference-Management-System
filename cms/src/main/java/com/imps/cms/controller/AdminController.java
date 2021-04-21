package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.dto.ConferenceDto;
import com.imps.cms.model.dto.DeadlineDto;
import com.imps.cms.model.dto.InvitationDto;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private DeadlineService deadlineService;
    @Autowired
    private InvitationService invitationService;

    @PostMapping("/addAdmin")
    public ResponseEntity<UserRole> addAdmin(@Valid @RequestBody UserRoleDto userRoleDto) throws URISyntaxException {
        UserRole userRole = UserRole.builder()
                .user(userService.findById(userRoleDto.getUserId()))
                .conference(conferenceService.findById(userRoleDto.getConferenceId()))
                .build();
        userRoleService.addUserRole(userRole);
        return ResponseEntity.created(new URI("/api/userRole/" + userRole.getId())).body(userRole);
    }

    @PostMapping("/conference")
    public ResponseEntity<Conference> addConference(@Valid @RequestBody ConferenceDto conferenceDto) throws URISyntaxException {
        Conference conference = Conference.builder().title(conferenceDto.getTitle()).build();
        conferenceService.addConference(conference);
        return ResponseEntity.created(new URI("/api/conference/" + conference.getId())).body(conference);
    }

    @PostMapping("/deadline")
    public ResponseEntity<Deadline> addDeadline(@Valid @RequestBody DeadlineDto deadlineDto) throws URISyntaxException {
        Deadline deadline = Deadline.builder()
                .conference(conferenceService.findById(deadlineDto.getConferenceId()))
                .deadlineType(deadlineDto.getDeadlineType())
                .build();
                // todo set the date properly
        deadlineService.addDeadline(deadline);
        return ResponseEntity.created(new URI("/api/deadline/" + deadline.getId())).body(deadline);
    }

    @PostMapping("/invitation")
    public ResponseEntity<Invitation> inviteChair(@Valid @RequestBody InvitationDto invitationDto) throws URISyntaxException {
        Invitation invitation = Invitation.builder()
                .receiver(userService.findById(invitationDto.getReceiverId()))
                .sender(userService.findById(invitationDto.getSenderId()))
                .text(invitationDto.getText())
                .token(invitationDto.getToken())
                .build();

        invitationService.addInvitation(invitation);
        invitationService.sendInvitationEmail(invitation);
        return ResponseEntity.created(new URI("api/invitation/" + invitation.getId())).body(invitation);
    }
}
