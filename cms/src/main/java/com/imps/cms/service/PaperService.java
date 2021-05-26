package com.imps.cms.service;

import com.imps.cms.model.*;
import com.imps.cms.model.dto.PaperDto;
import com.imps.cms.model.dto.PaperServerToWebDto;
import com.imps.cms.repository.PaperRepository;
import com.imps.cms.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaperService {
    @Autowired
    public PaperRepository paperRepository;

    @Autowired
    public ProposalRepository proposalRepository;

    public Paper findById(Long id){
        return paperRepository.findById(id).orElseThrow(() -> new RuntimeException("no paper with this id"));
    }

    public void updatePaper(Paper paper){
        paperRepository.save(paper);
    }

    public List<Paper> findAll(){
        return this.paperRepository.findAll();
    }

    public Long addFile(MultipartFile file, String title, String subject, String keywords, String topics, User author, Conference conference) throws IOException{
        Paper paper = Paper.builder()
                .title(title)
                .filename(file.getOriginalFilename())
                .data(file.getBytes())
                .subject(subject)
                .keywords(keywords)
                .topics(topics)
                .author(author)
                .conference(conference)
                .build();
        return paperRepository.save(paper).getId();
    }

    public Long updatePaper(MultipartFile file, String title, String subject, String keywords, String topics, User author, Conference conference, Long paperId) throws IOException {
        Paper paper = Paper.builder()
                .title(title)
                .filename(file.getOriginalFilename())
                .data(file.getBytes())
                .subject(subject)
                .keywords(keywords)
                .topics(topics)
                .author(author)
                .section(paperRepository.findById(paperId).orElseThrow(() -> new RuntimeException("no sections with this id")).getSection())
                .conference(conference)
                .build();
        paper.setId(paperId);
        return paperRepository.save(paper).getId();
    }

    public List<Paper> getAcceptedNotAssigned(Conference conference) {
        return paperRepository.findByConference(conference).stream()
                .filter(paper -> proposalRepository.findByPaper(paper).stream().anyMatch(proposal -> proposal.getStatus().equals("ACCEPTED")))
                .filter(paper -> paper.getSection() == null)
                .collect(Collectors.toList());
    }

    public Paper findBySection(Section section) {
        return paperRepository.findBySection(section);
    }

    public Paper updateSection(Paper paper) {
        return paperRepository.save(paper);
    }
}
