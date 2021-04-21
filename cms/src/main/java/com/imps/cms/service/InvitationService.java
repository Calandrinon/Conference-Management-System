package com.imps.cms.service;

import com.imps.cms.model.Invitation;
import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import com.imps.cms.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;

    public void addInvitation(Invitation invitation){
        this.invitationRepository.save(invitation);
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
}
