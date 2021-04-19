package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "conferenceDto"
        , types = { Conference.class }
)
public interface ConferenceDto {
    Long getId();
    String getTitle();
}
