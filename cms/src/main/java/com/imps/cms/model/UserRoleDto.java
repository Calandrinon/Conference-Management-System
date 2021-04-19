package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "userRoleDto"
        , types = { UserRole.class }
)
public interface UserRoleDto {
    long getId();

    UserDto getUser();

    ConferenceDto getConference();

    UserType userType();
}
