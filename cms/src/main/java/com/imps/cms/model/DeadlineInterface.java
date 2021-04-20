package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(
        name = "deadlineDto"
        , types = { Deadline.class }
)
public interface DeadlineInterface {
    Long getId();
    ConferenceInterface getConference();
    Date getDate();
    DeadlineType getDeadlineType();
}
