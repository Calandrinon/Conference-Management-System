package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.dto.SectionDto;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.SectionRepository;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.repository.UserRoleRepository;
import com.imps.cms.service.ConferenceService;
import com.imps.cms.service.SectionService;
import com.imps.cms.service.UserRoleService;
import com.imps.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ListenerController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private SectionService sectionService;

    @PostMapping("/addListener")
    public ResponseEntity<UserRole> addListener(@Valid @RequestBody UserRoleDto userRoleDto) throws URISyntaxException {
        UserRole userRole = UserRole.builder()
                .user(userService.findById(userRoleDto.getUserId()))
                .conference(conferenceService.findById(userRoleDto.getConferenceId()))
                .userType(UserType.LISTENER)
                .build();

        userRoleService.addUserRole(userRole);
        return ResponseEntity.created(new URI("/api/addListener/" + userRole.getId())).body(userRole);
    }

    @PutMapping("/selectSection/{userId}")
    public ResponseEntity<User> selectSection(@PathVariable Long userId, @Valid @RequestBody SectionDto sectionDto){
        User listener = userService.findById(userId);
        Section section = sectionService.findById(sectionDto.getId());
        listener.setSection(section);
        userService.updateUser(listener);
        return ResponseEntity.ok().body(listener);
    }
}
