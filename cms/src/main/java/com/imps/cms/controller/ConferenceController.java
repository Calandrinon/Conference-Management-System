package com.imps.cms.controller;
import com.imps.cms.model.Conference;
import com.imps.cms.model.Deadline;
import com.imps.cms.model.converter.UserRoleConverter;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.service.ConferenceService;
import com.imps.cms.service.DeadlineService;
import com.imps.cms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private DeadlineService deadlineService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/conferences")
    public ResponseEntity<List<Conference>> getConferences() {
        return new ResponseEntity<>(conferenceService.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/deadlines/{conferenceId}")
    public ResponseEntity<List<Deadline>> getDeadlineForConference(@PathVariable Long conferenceId){
        return new ResponseEntity<>(deadlineService.findByConferenceId(conferenceId), HttpStatus.OK);
    }

    @RequestMapping("/userRoles/{conferenceId}/{userId}")
    public ResponseEntity<List<UserRoleDto>> getRolesForUserPerConference(@PathVariable Long conferenceId, @PathVariable Long userId){
        return new ResponseEntity<>(userRoleService.findByConferenceIdAndUserId(conferenceId, userId).stream().map(UserRoleConverter::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }
}
