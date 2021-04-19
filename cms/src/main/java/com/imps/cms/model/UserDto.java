package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "userDto"
        , types = { User.class }
)
public interface UserDto {

    long getId();

    String getFullName();

    SectionDto getSection();

}
