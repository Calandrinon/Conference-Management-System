package com.imps.cms.model.converter;

import com.imps.cms.model.Proposal;
import com.imps.cms.model.User;
import com.imps.cms.model.dto.UserDto;

import java.util.stream.Collectors;

public class UserConverter {
    public static UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setSalt(user.getSalt());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
