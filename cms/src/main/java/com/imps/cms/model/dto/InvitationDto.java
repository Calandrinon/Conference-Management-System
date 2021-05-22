package com.imps.cms.model.dto;


import com.imps.cms.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDto implements Serializable {
    private Long conferenceId;
    private Long senderId;
    private Long receiverId;
    private UserType userType;
    private String status;
}
