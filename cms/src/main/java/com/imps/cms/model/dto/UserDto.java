package com.imps.cms.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String fullName;
    private String salt;
    private String email;
    private String password;
    private Long sectionId;
    private List<Long> proposalIds;
    private boolean activated;
}
