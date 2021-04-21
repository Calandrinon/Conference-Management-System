package com.imps.cms.controller;
import com.imps.cms.model.Conference;
import com.imps.cms.repository.ConferenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConferenceController {
    private final ConferenceRepository conferenceRepository;

    public ConferenceController(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @RequestMapping("/conferences")
    public List<Conference> getConferences() {
        return this.conferenceRepository.findAll();
    }
}
