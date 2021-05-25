package com.imps.cms.service;
import com.imps.cms.model.Conference;
import com.imps.cms.model.User;
import com.imps.cms.model.UserRole;
import com.imps.cms.repository.ActivationTokenRepository;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PermissionService {

    private static int adminID = 69420;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivationTokenRepository activationTokenRepository;


    public boolean isAccountActivated(Long userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("no user with this id!\n"));
        return user.isActivated();
    }

    public boolean isAccountAdmin(Long userID) {
        return userID == adminID;
    }

    public boolean isAccountListener(Long conferenceID, Long userID) {
        Conference conference = conferenceRepository.findById(conferenceID).orElseThrow(() -> new RuntimeException("no conference with this id!\n"));
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("no user with this id!\n"));
        UserRole userRole = userRoleRepository.findByConferenceAndUser(conference, user);

        return userRole.getIsListener();
    }

    public boolean isAccountAuthor(Long conferenceID, Long userID) {
        Conference conference = conferenceRepository.findById(conferenceID).orElseThrow(() -> new RuntimeException("no conference with this id!\n"));
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("no user with this id!\n"));
        UserRole userRole = userRoleRepository.findByConferenceAndUser(conference, user);

        return userRole.getIsAuthor();
    }

    public boolean isAccountPCMember(Long conferenceID, Long userID) {
        Conference conference = conferenceRepository.findById(conferenceID).orElseThrow(() -> new RuntimeException("no conference with this id!\n"));
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("no user with this id!\n"));
        UserRole userRole = userRoleRepository.findByConferenceAndUser(conference, user);

        return userRole.getIsPcMember();
    }

    public boolean isAccountChair(Long conferenceID, Long userID) {
        Conference conference = conferenceRepository.findById(conferenceID).orElseThrow(() -> new RuntimeException("no conference with this id!\n"));
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("no user with this id!\n"));
        UserRole userRole = userRoleRepository.findByConferenceAndUser(conference, user);

        return userRole.getIsChair();
    }
}
