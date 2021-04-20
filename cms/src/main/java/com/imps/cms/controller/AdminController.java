package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.dto.ConferenceDto;
import com.imps.cms.model.dto.DeadlineDto;
import com.imps.cms.model.dto.InvitationDto;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.repository.*;
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
    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final DeadlineRepository deadlineRepository;
    private final InvitationRepository invitationRepository;


    public AdminController(ConferenceRepository conferenceRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, DeadlineRepository deadlineRepository, InvitationRepository invitationRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.deadlineRepository = deadlineRepository;
        this.invitationRepository = invitationRepository;
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<UserRole> addAdmin(@Valid @RequestBody UserRoleDto userRoleDto) throws URISyntaxException {
        UserRole userRole = UserRole.builder()
                .user(userRepository.findById(userRoleDto.getUserId()).orElseThrow(() -> new RuntimeException("no user with this id")))
                .conference(conferenceRepository.findById(userRoleDto.getConferenceId()).orElseThrow(() -> new RuntimeException("no conference with this id")))
                .userType(UserType.ADMIN)
                .build();
        userRoleRepository.save(userRole);
        return ResponseEntity.created(new URI("/api/userRole/" + userRole.getId())).body(userRole);
    }

    @PostMapping("/conference")
    public ResponseEntity<Conference> addConference(@Valid @RequestBody ConferenceDto conferenceDto) throws URISyntaxException {
        Conference conference = Conference.builder().title(conferenceDto.getTitle()).build();
        conferenceRepository.save(conference);
        return ResponseEntity.created(new URI("/api/conference/" + conference.getId())).body(conference);
    }

    @PostMapping("/deadline")
    public ResponseEntity<Deadline> addDeadline(@Valid @RequestBody DeadlineDto deadlineDto) throws URISyntaxException {
        Deadline deadline = Deadline.builder()
                .conference(conferenceRepository.findById(deadlineDto.getConferenceId()).orElseThrow(() -> new RuntimeException("no conference with this id")))
                .deadlineType(deadlineDto.getDeadlineType())
                .build();
                // todo set the date properly
        deadlineRepository.save(deadline);
        return ResponseEntity.created(new URI("/api/deadline/" + deadline.getId())).body(deadline);
    }

    private void sendInvitationEmail(@Valid @RequestBody Invitation invitation){
        String to = invitation.getReceiver().getEmail();
        String from = invitation.getSender().getEmail();
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject("Wannabe a chair?");
            mimeMessage.setText(invitation.getText() + " Token: " + invitation.getToken());
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/invitation")
    public ResponseEntity<Invitation> inviteChair(@Valid @RequestBody InvitationDto invitationDto) throws URISyntaxException {
        Invitation invitation = Invitation.builder()
                .receiver(userRepository.findById(invitationDto.getReceiverId()).orElseThrow(() -> new RuntimeException("no user with this id")))
                .sender(userRepository.findById(invitationDto.getSenderId()).orElseThrow(() -> new RuntimeException("no user with this id")))
                .text(invitationDto.getText())
                .token(invitationDto.getToken())
                .build();

        invitationRepository.save(invitation);
        this.sendInvitationEmail(invitation);
        return ResponseEntity.created(new URI("api/invitation/" + invitation.getId())).body(invitation);
    }
}
