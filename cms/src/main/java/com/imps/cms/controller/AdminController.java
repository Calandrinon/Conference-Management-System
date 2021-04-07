package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.repository.*;
import org.springframework.stereotype.Controller;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

@Controller
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

    public void addAdmin(long userID, long conferenceID){
        UserRole userRole = new UserRole(userRepository.getOne(userID), conferenceRepository.getOne(conferenceID), UserType.ADMIN);
        userRoleRepository.save(userRole);
    }

    public void addConference(String title){
        Conference conference = new Conference(title);
        conferenceRepository.save(conference);
    }

    public void addDeadline(long conferenceID, Date date, DeadlineType deadlineType){
        Deadline deadline = new Deadline(this.conferenceRepository.getOne(conferenceID), date, deadlineType);
        deadlineRepository.save(deadline);
    }

    public void addDeadlines(long conferenceID, Date date, DeadlineType deadlineType){
        Deadline deadline = new Deadline(conferenceRepository.getOne(conferenceID), date, deadlineType);
        deadlineRepository.save(deadline);
    }

    private void sendInvitationEmail(Invitation invitation){
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

    public void inviteChair(long receiverId, long senderId, String text, String token){
        Invitation invitation = new Invitation(userRepository.getOne(receiverId), userRepository.getOne(senderId), text, token);
        invitationRepository.save(invitation);
        this.sendInvitationEmail(invitation);
    }
}
