package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "sectionDto"
        , types = { Section.class }
)
public interface SectionInterface {
    Long getId();
    String getName();
    UserDtoInterface getSupervisor();
    ConferenceInterface getConference();
}
