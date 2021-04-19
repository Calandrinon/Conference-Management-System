package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "sectionDto"
        , types = { Section.class }
)
public interface SectionDto {
    Long getId();
    String getName();
    UserDto getSupervisor();
    ConferenceDto getConference();
}
