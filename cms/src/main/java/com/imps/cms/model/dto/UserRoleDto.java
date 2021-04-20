package com.imps.cms.model.dto;

import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto implements Serializable {
    private Long id;
    private Long conferenceId;
    private Long userId;
    private UserType userType;
}
