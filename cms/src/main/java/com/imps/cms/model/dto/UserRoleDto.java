package com.imps.cms.model.dto;

import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Dictionary;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto implements Serializable {
    private Long id;
    private Long conferenceId;
    private Long userId;
    private Boolean isChair;
    private Boolean isAuthor;
    private Boolean isPcMember;
    private Boolean isListener;
}
