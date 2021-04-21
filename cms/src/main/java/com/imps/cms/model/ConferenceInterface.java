package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "conferenceDto"
        , types = { Conference.class }
)
public interface ConferenceInterface {
    Long getId();
    String getTitle();
}
