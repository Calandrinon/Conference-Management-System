package com.imps.cms.controller;

import com.imps.cms.model.*;
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
    public ResponseEntity<UserRole> addListener(@Valid @RequestBody UserRoleDto userRoleDto) throws URISyntaxException {
        UserRole userRole = UserRole.builder()
                .user(userRepository.getOne(userRoleDto.getUser().getId()))
                .conference(conferenceRepository.getOne(userRoleDto.getConference().getId()))
                .userType(UserType.LISTENER)
                .build();

        userRoleRepository.save(userRole);
        return ResponseEntity.created(new URI("/api/addListener/" + userRole.getId())).body(userRole);
    }

    @PutMapping("/selectSection/{userId}")
    public ResponseEntity<User> selectSection(@PathVariable Long userId, @Valid @RequestBody SectionDto sectionDto){
        User listener = userRepository.getOne(userId);
        Section section = sectionRepository.getOne(sectionDto.getId());
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
