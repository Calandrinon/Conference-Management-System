package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "invitationDto"
        , types = { Invitation.class }
)
public interface InvitationDto {
    Long getId();
    UserDto getSender();
    UserDto getReceiver();
    String getText();
    String getToken();
}
