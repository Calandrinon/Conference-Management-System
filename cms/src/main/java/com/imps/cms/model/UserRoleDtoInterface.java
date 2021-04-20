package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "userRoleDto"
        , types = { UserRole.class }
)
public interface UserRoleDtoInterface {
    long getId();

    UserDtoInterface getUser();

    ConferenceInterface getConference();

    UserType userType();
}
