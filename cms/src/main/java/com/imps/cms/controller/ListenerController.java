package com.imps.cms.controller;

import com.imps.cms.model.Section;
import com.imps.cms.model.User;
import com.imps.cms.model.UserRole;
import com.imps.cms.model.UserType;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.SectionRepository;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.repository.UserRoleRepository;
import org.springframework.stereotype.Controller;

@Controller
public class ListenerController {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ConferenceRepository conferenceRepository;
    private final SectionRepository sectionRepository;

    public ListenerController(UserRepository userRepository, UserRoleRepository userRoleRepository, ConferenceRepository conferenceRepository, SectionRepository sectionRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.conferenceRepository = conferenceRepository;
        this.sectionRepository = sectionRepository;
    }

    public void addListener(long userID, long conferenceID){
        UserRole userRole = new UserRole(userRepository.getOne(userID), conferenceRepository.getOne(conferenceID), UserType.LISTENER);
        userRoleRepository.save(userRole);
    }

    public void selectSection(long userID, long sectionID){
        User listener = userRepository.getOne(userID);
        Section section = sectionRepository.getOne(sectionID);
        listener.setSection(section);
        userRepository.save(listener);
    }


}
