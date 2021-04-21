package com.imps.cms.controller;
import com.imps.cms.model.Conference;
import com.imps.cms.model.Deadline;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.service.ConferenceService;
import com.imps.cms.service.DeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private DeadlineService deadlineService;

    @RequestMapping("/conferences")
    public ResponseEntity<List<Conference>> getConferences() {
        return new ResponseEntity<>(conferenceService.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/deadlines/{conferenceId}")
    public ResponseEntity<List<Deadline>> getDeadlineForConference(@PathVariable Long conferenceId){
        return new ResponseEntity<>(deadlineService.findByConferenceId(conferenceId), HttpStatus.OK);
    }
}
