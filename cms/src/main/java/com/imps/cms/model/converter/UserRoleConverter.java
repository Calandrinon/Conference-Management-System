package com.imps.cms.model.converter;

import com.imps.cms.model.UserRole;
import com.imps.cms.model.UserType;
import com.imps.cms.model.dto.UserRoleDto;

public class UserRoleConverter {
    public static UserRoleDto convertToDto(UserRole userRole){
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setId(userRole.getId());
        userRoleDto.setUserId(userRole.getUser().getId());
        userRoleDto.setConferenceId(userRole.getConference().getId());
        userRoleDto.setIsAuthor(userRole.getIsAuthor());
        userRoleDto.setIsChair(userRole.getIsChair());
        userRoleDto.setIsListener(userRole.getIsListener());
        userRoleDto.setIsPcMember(userRole.getIsPcMember());
        return userRoleDto;
    }
}
