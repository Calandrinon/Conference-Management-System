package com.imps.cms.controller;
import com.imps.cms.model.Conference;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping("/conferences")
    public List<Conference> getConferences() {
        return this.conferenceService.findAll();
    }
}
