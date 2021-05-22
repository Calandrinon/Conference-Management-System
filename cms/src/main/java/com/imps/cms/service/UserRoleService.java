package com.imps.cms.service;


import com.imps.cms.model.Conference;
import com.imps.cms.model.User;
import com.imps.cms.model.UserRole;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private UserRepository userRepository;

    public void addUserRole(UserRole userRole){
        userRoleRepository.save(userRole);
    }

    public List<UserRole> findByConferenceIdAndUserId(Long conferenceId, Long userId) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        User user = userRepository.findById(userId).get();
        return userRoleRepository.findByConferenceAndUser(conference, user);
    }

    public UserRole updateUserRole(UserRole userRole) {
        return  userRoleRepository.save(userRole);
    }

    public void setEmptyUserRole(Conference conference, User user){
        UserRole userRole =  new UserRole();
        userRole.setUser(user);
        userRole.setConference(conference);
        userRole.setIsChair(false);
        userRole.setIsListener(false);
        userRole.setIsPcMember(false);
        userRole.setIsAuthor(false);
        userRoleRepository.save(userRole);
    }
}
