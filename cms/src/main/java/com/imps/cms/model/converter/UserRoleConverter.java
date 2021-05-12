package com.imps.cms.model.converter;

import com.imps.cms.model.UserRole;
import com.imps.cms.model.dto.UserRoleDto;

public class UserRoleConverter {
    public static UserRoleDto convertToDto(UserRole userRole){
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setUserId(userRole.getUser().getId());
        userRoleDto.setUserType(userRole.getUserType());
        userRoleDto.setConferenceId(userRole.getConference().getId());
        userRoleDto.setId(userRole.getId());
        return userRoleDto;
    }
}
