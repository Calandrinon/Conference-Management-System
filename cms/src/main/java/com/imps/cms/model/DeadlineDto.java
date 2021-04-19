package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(
        name = "deadlineDto"
        , types = { Deadline.class }
)
public interface DeadlineDto {
    Long getId();
    ConferenceDto getConference();
    Date getDate();
    DeadlineType getDeadlineType();
}
