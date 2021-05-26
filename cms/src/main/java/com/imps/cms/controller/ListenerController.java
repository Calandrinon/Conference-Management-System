package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.UserRoleConverter;
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
import org.springframework.http.HttpStatus;
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

    @GetMapping("/add-listener/{conferenceId}/{userId}")
    public ResponseEntity<UserRoleDto> addListener(@PathVariable Long conferenceId, @PathVariable Long userId) throws URISyntaxException {
        UserRole userRole = userRoleService.findByConferenceIdAndUserId(conferenceId, userId);
        userRole.setIsListener(true);
        return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.updateUserRole(userRole)), HttpStatus.OK);
    }

    @GetMapping("/choose-section/{sectionId}/{userId}")
    public ResponseEntity<UserRoleDto> chooseSection(@PathVariable Long sectionId, @PathVariable Long userId){
        Section section = sectionService.findById(sectionId);
        UserRole userRole = userRoleService.findByConferenceIdAndUserId(userId, section.getConference().getId());
        userRole.setSection(section);
        userRoleService.updateUserRole(userRole);
        return new ResponseEntity<>(UserRoleConverter.convertToDto(userRole), HttpStatus.OK);
    }
}
