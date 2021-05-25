package com.imps.cms.controller;
import com.imps.cms.model.Conference;
import com.imps.cms.model.converter.UserRoleConverter;
import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.service.ConferenceService;
import com.imps.cms.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/conferences")
    public ResponseEntity<List<Conference>> getConferences() {
        return new ResponseEntity<>(conferenceService.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/user-roles/{conferenceId}/{userId}")
    public ResponseEntity<UserRoleDto> getRolesForUserPerConference(@PathVariable Long conferenceId, @PathVariable Long userId){
        return new ResponseEntity<>(UserRoleConverter.convertToDto(userRoleService.findByConferenceIdAndUserId(conferenceId, userId)), HttpStatus.OK);
    }
}
