package com.imps.cms.model.converter;

import com.imps.cms.model.Paper;
import com.imps.cms.model.dto.PaperDto;

public class PaperConverter {
    public static PaperDto convertToDto(Paper paper){
        PaperDto paperDto = new PaperDto();
        paperDto.setFileName(paper.getFilename());
        paperDto.setId(paper.getId());
        paperDto.setKeywords(paper.getKeywords());
        paperDto.setSubject(paper.getSubject());
        paperDto.setTitle(paper.getTitle());
        paperDto.setTopics(paper.getTopics());
        paperDto.setAuthorId(paper.getAuthor().getId());
        if(paper.getSection() != null)
            paperDto.setSectionId(paper.getSection().getId());
        else paperDto.setSectionId(0L);
        return paperDto;
    }
}
