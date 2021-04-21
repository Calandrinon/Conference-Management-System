package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "invitationDto"
        , types = { Invitation.class }
)
public interface InvitationInterface {
    Long getId();
    UserDtoInterface getSender();
    UserDtoInterface getReceiver();
    String getText();
    String getToken();
    UserType userType();
}
