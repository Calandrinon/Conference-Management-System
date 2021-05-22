package com.imps.cms.model.converter;

import com.imps.cms.model.Conference;
import com.imps.cms.model.dto.ConferenceDto;

public class ConferenceConverter {
    public static ConferenceDto convertToDto(Conference conference){
        return new ConferenceDto();
    }
}
