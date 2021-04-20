package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "userDto"
        , types = { User.class }
)
public interface UserDtoInterface {

    long getId();

    String getFullName();

    SectionInterface getSection();

}
