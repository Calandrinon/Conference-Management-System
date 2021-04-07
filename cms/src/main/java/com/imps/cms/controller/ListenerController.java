package com.imps.cms.controller;

import com.imps.cms.model.Section;
import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import com.imps.cms.repository.SectionRepository;
import com.imps.cms.repository.UserRepository;
import org.springframework.stereotype.Controller;

@Controller
public class ListenerController {
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;

    public ListenerController(UserRepository userRepository, SectionRepository sectionRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
    }

    public void addListener(String name, String salt, String email, String password){
        User user = new User(name, UserType.LISTENER, salt, email, password);
        userRepository.save(user);
    }

    public void selectSection(long userID, long sectionID){
        User listener = userRepository.getOne(userID);
        Section section = sectionRepository.getOne(sectionID);
        listener.setSection(section);
        userRepository.save(listener);
    }


}
