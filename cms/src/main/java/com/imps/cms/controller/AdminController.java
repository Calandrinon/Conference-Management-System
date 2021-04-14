package com.imps.cms.controller;

import com.imps.cms.model.*;
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
import java.util.Date;
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
    public ResponseEntity<UserRole> addAdmin(@Valid @RequestBody UserRole userRole) throws URISyntaxException {
        // long userID, long conferenceID
        /*
        * UserRole userRole = UserRole.builder()
                .user(userRepository.getOne(userID))
                .conference(conferenceRepository.getOne(conferenceID))
                .userType(UserType.ADMIN)
                .build();
        * */
        // UserRole userRole = new UserRole(userRepository.getOne(userID), conferenceRepository.getOne(conferenceID), UserType.ADMIN);
        userRoleRepository.save(userRole);
        // return ResponseEntity.created(new URI("/api/userRole/" + userRole.getId())).body(userRole);
        return ResponseEntity.ok(userRole);
    }

    @PostMapping("/conference")
    public ResponseEntity<Conference> addConference(@Valid @RequestBody Conference conference) throws URISyntaxException {
        // String title
        /*
        * Conference conference = Conference.builder().title(title).build();
        * */
        conferenceRepository.save(conference);
        return ResponseEntity.created(new URI("/api/conference/" + conference.getId())).body(conference);
    }

    @PostMapping("/deadline")
    public ResponseEntity<Deadline> addDeadline(@Valid @RequestBody Deadline deadline) throws URISyntaxException {
        // long conferenceID, Date date, DeadlineType deadlineType
        /*
        Deadline deadline = Deadline.builder()
                .conference(conferenceRepository.getOne(conferenceID))
                .date(date)
                .deadlineType(deadlineType)
                .build();

         */
        // Deadline deadline = new Deadline(this.conferenceRepository.getOne(conferenceID), date, deadlineType);
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
    public ResponseEntity<Invitation> inviteChair(@Valid @RequestBody Invitation invitation) throws URISyntaxException {
        // long receiverId, long senderId, String text, String token
        /*
        Invitation invitation = Invitation.builder()
                .receiver(userRepository.getOne(receiverId))
                .sender(userRepository.getOne(senderId))
                .text(text)
                .token(token)
                .build();
         */
        // Invitation invitation = new Invitation(userRepository.getOne(receiverId), userRepository.getOne(senderId), text, token);
        invitationRepository.save(invitation);
        this.sendInvitationEmail(invitation);
        return ResponseEntity.created(new URI("api/invitation/" + invitation.getId())).body(invitation);
    }
}
