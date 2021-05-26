package com.imps.cms.model.converter;

import com.imps.cms.model.Section;
import com.imps.cms.model.dto.SectionDto;

public class SectionConverter {
    public static SectionDto convertToDto(Section section){
        SectionDto sectionDto = new SectionDto();
        sectionDto.setId(section.getId());
        sectionDto.setConferenceId(section.getConference().getId());
        sectionDto.setSupervisorId(section.getSupervisor().getId());
        sectionDto.setName(section.getName());
        return sectionDto;
    }
}
