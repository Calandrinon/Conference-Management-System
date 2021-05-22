package com.imps.cms.service;

import com.imps.cms.model.Conference;
import com.imps.cms.model.Invitation;
import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.InvitationRepository;
import com.imps.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private UserRepository userRepository;

    public Invitation addInvitation(Invitation invitation){
        return this.invitationRepository.save(invitation);
    }

    public List<Invitation> getAll(){
        return this.invitationRepository.findAll();
    }

    public Invitation findById(long id){
        return invitationRepository.findById(id).orElseThrow(() -> new RuntimeException("no user with this id"));
    }

    public void sendInvitationEmail(Invitation invitation){
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
            mimeMessage.setSubject("Wannabe a " + invitation.getUserType().toString() + " ?");
            mimeMessage.setText(invitation.getText() + " Token: " + invitation.getToken());
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public List<Invitation> findByReceiver(Long userId) {
        return invitationRepository.findByReceiverId(userId);
    }

    public Invitation getChairInvitations(Long conferenceId, Long userId) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        User receiver = userRepository.findById(userId).get();
        List<Invitation> invitations = invitationRepository.findByConferenceAndReceiverAndUserType(conference, receiver, UserType.CHAIR).stream().filter(invitation -> invitation.getStatus().equals("PENDING")).collect(Collectors.toList());
        if(invitations.size() > 0)
            return invitations.get(0);
        return null;
    }

    public void cancelChairInvitation(Long conferenceId, Long receiverId) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        User receiver = userRepository.findById(receiverId).get();
        for(Invitation invitation: invitationRepository.findByConferenceAndReceiverAndUserType(conference, receiver, UserType.CHAIR)){
            invitation.setStatus("CANCELED");
            invitationRepository.save(invitation);
        }
    }

    public Invitation updateInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    public Invitation getPcMemberInvitations(Long conferenceId, Long userId) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        User receiver = userRepository.findById(userId).get();
        List<Invitation> invitations = invitationRepository.findByConferenceAndReceiverAndUserType(conference, receiver, UserType.PC_MEMBER).stream().filter(invitation -> invitation.getStatus().equals("PENDING")).collect(Collectors.toList());
        if(invitations.size() > 0)
            return invitations.get(0);
        return null;
    }

    public void cancelPcMemberInvitation(Long conferenceId, Long receiverId) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        User receiver = userRepository.findById(receiverId).get();
        for(Invitation invitation: invitationRepository.findByConferenceAndReceiverAndUserType(conference, receiver, UserType.PC_MEMBER)){
            invitation.setStatus("CANCELED");
            invitationRepository.save(invitation);
        }
    }
}
