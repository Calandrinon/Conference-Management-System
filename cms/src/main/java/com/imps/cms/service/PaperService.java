package com.imps.cms.service;

import com.imps.cms.model.Conference;
import com.imps.cms.model.Paper;
import com.imps.cms.model.Section;
import com.imps.cms.model.User;
import com.imps.cms.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class PaperService {
    @Autowired
    public PaperRepository paperRepository;

    public Paper findById(Long id){
        return paperRepository.findById(id).orElseThrow(() -> new RuntimeException("no paper with this id"));
    }

    public void updatePaper(Paper paper){
        paperRepository.save(paper);
    }

    public List<Paper> findAll(){
        return this.paperRepository.findAll();
    }

    public Long addFile(MultipartFile file, String title, String subject, String keywords, String topics, User author, Section section, Conference conference) throws IOException{
        Paper paper = Paper.builder()
                .title(title)
                .filename(file.getOriginalFilename())
                .data(file.getBytes())
                .subject(subject)
                .keywords(keywords)
                .topics(topics)
                .author(author)
                .section(section)
                .conference(conference)
                .build();

        return paperRepository.save(paper).getId();
    }
}
