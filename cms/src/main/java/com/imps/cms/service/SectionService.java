package com.imps.cms.service;

import com.imps.cms.model.Conference;
import com.imps.cms.model.Section;
import com.imps.cms.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public Section findById(Long id){
        return sectionRepository.findById(id).orElseThrow(() -> new RuntimeException("No section with this id"));
    }

    public Section addSection(Section section){
        return sectionRepository.save(section);
    }


    public List<Section> findAll() {
        return this.sectionRepository.findAll();
    }

    public List<Section> getForConference(Conference conference) {
        return sectionRepository.findByConference(conference);
    }
}
