package com.imps.cms.service;

import com.imps.cms.model.Conference;
import com.imps.cms.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {
    @Autowired
    private ConferenceRepository conferenceRepository;

    public Conference findById(Long id){
        return conferenceRepository.findById(id).orElseThrow(() -> new RuntimeException("No conference with this id"));
    }

    public void addConference(Conference conference){
        conferenceRepository.save(conference);
    }
}
