package com.imps.cms.service;

import com.imps.cms.model.Conference;
import com.imps.cms.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceService {
    @Autowired
    private ConferenceRepository conferenceRepository;

    public Conference findById(Long id){
        return conferenceRepository.findById(id).orElseThrow(() -> new RuntimeException("No conference with this id"));
    }

    public Conference addConference(Conference conference){
        return conferenceRepository.save(conference);
    }

    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }
}
