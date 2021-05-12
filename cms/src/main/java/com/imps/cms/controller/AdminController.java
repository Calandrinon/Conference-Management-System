package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.DeadlineConverter;
import com.imps.cms.model.converter.UserConverter;
import com.imps.cms.model.dto.*;
import com.imps.cms.repository.*;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
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
    private DeadlineService deadlineService;
    @Autowired
    private InvitationService invitationService;

    @PostMapping("/add-conference")
    public ResponseEntity<Conference> addConference(@Valid @RequestBody ConferenceDto conferenceDto) throws URISyntaxException {
        Conference conference = new Conference();
        conference.setTitle(conferenceDto.getTitle());
        return new ResponseEntity<>(conferenceService.addConference(conference), HttpStatus.OK);
    }

    @PostMapping("/add-deadline")
    public ResponseEntity<DeadlineDto> addDeadline(@Valid @RequestBody DeadlineDto deadlineDto) throws URISyntaxException {
        Deadline deadline = Deadline.builder()
                .conference(conferenceService.findById(deadlineDto.getConferenceId()))
                .deadlineType(deadlineDto.getDeadlineType())
                .date(deadlineDto.getDate())
                .build();
        return new ResponseEntity<>(DeadlineConverter.convertToDto(deadlineService.addDeadline(deadline)), HttpStatus.OK);
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
