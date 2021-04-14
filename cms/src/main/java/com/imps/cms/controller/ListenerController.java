package com.imps.cms.controller;

import com.imps.cms.model.Section;
import com.imps.cms.model.User;
import com.imps.cms.model.UserRole;
import com.imps.cms.model.UserType;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.SectionRepository;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.repository.UserRoleRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
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

    @PostMapping("/addListener")
    public ResponseEntity<UserRole> addListener(@Valid @RequestBody UserRole userRole) throws URISyntaxException {
        // long userID, long conferenceID
        /*
        UserRole userRole = UserRole.builder()
                .user(userRepository.getOne(userID))
                .conference(conferenceRepository.getOne(conferenceID))
                .userType(UserType.LISTENER)
                .build();

         */
        // UserRole userRole = new UserRole(userRepository.getOne(userID), conferenceRepository.getOne(conferenceID), UserType.LISTENER);
        userRoleRepository.save(userRole);
        return ResponseEntity.created(new URI("/api/addListener/" + userRole.getId())).body(userRole);
    }

    // @PutMapping("/userRole/{id}")
    public ResponseEntity<User> selectSection(@Valid @RequestBody User listener, @Valid @RequestBody Section section){
        // User listener = userRepository.getOne(userID);
        // Section section = sectionRepository.getOne(sectionID);
        listener.setSection(section);
        userRepository.save(listener);
        return ResponseEntity.ok().body(listener);
    }
/*
*     @PutMapping("/group/{id}")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        log.info("Request to update group: {}", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }
* */

}
