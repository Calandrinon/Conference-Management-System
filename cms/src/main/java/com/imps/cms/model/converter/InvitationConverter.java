package com.imps.cms.model.converter;

import com.imps.cms.model.Invitation;
import com.imps.cms.model.dto.InvitationDto;

public class InvitationConverter {
    public static InvitationDto convertToDto(Invitation invitation){
        InvitationDto invitationDto = new InvitationDto();
        invitationDto.setReceiverId(invitation.getReceiver().getId());
        invitationDto.setSenderId(invitation.getSender().getId());
        invitationDto.setUserType(invitation.getUserType());
        invitationDto.setConferenceId(invitation.getConference().getId());
        invitationDto.setStatus(invitation.getStatus());
        return invitationDto;
    }
}
