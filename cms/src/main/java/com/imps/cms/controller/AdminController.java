package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.DeadlineRepository;
import com.imps.cms.repository.InvitationRepository;
import com.imps.cms.repository.UserRepository;
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
    private final DeadlineRepository deadlineRepository;
    private final InvitationRepository invitationRepository;


    public AdminController(ConferenceRepository conferenceRepository, UserRepository userRepository, DeadlineRepository deadlineRepository, InvitationRepository invitationRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.deadlineRepository = deadlineRepository;
        this.invitationRepository = invitationRepository;
    }

    public void addAdmin(String name, String salt, String email, String password){
        User admin = new User(name, UserType.ADMIN, salt, email, password);
        this.userRepository.save(admin);
    }

    public void addConference(String title, Date deadLine){
        Conference conference = new Conference(title, deadLine);
        conferenceRepository.save(conference);
    }

    public void addDeadlines(long conferenceID, Date date, DeadlineType deadlineType){
        Deadline deadline = new Deadline(conferenceRepository.getOne(conferenceID), date, deadlineType);
        deadlineRepository.save(deadline);
    }

    private void sendInvitationEmail(Invitation invitation){
        String to = invitation.getMail();
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

    public void inviteChair(String toEmail, long senderId, String text, String token){
        Invitation invitation = new Invitation(toEmail, userRepository.getOne(senderId), text, token);
        invitationRepository.save(invitation);
        this.sendInvitationEmail(invitation);
    }
}
