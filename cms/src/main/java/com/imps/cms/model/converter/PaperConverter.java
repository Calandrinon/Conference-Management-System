package com.imps.cms.model.converter;

import com.imps.cms.model.Paper;
import com.imps.cms.model.dto.PaperDto;

public class PaperConverter {
    public static PaperDto convertToDto(Paper paper){
        PaperDto paperDto = new PaperDto();
        paperDto.setFileName(paper.getFilename());
        paperDto.setId(paper.getID());
        paperDto.setKeywords(paper.getKeywords());
        paperDto.setSubject(paper.getSubject());
        paperDto.setTitle(paper.getTitle());
        paperDto.setTopics(paper.getTopics());
        paperDto.setAuthorId(paper.getAuthor().getId());
        paperDto.setSectionId(0L);
        return paperDto;
    }
}
