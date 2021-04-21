package com.imps.cms.service;

import com.imps.cms.model.Paper;
import com.imps.cms.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperService {
    @Autowired
    public PaperRepository paperRepository;

    public Paper findById(Long id){
        return paperRepository.findById(id).orElseThrow(() -> new RuntimeException("no paper with this id"));
    }

    public void addPaper(Paper paper){
        paperRepository.save(paper);
    }

    public void updatePaper(Paper paper){
        paperRepository.save(paper);
    }
}
