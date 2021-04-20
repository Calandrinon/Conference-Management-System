package com.imps.cms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDto implements Serializable {
    private Long id;
    private String name;
    private Long supervisorId;
    private Long conferenceId;
}
