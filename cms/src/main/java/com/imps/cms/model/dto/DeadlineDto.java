package com.imps.cms.model.dto;

import com.imps.cms.model.DeadlineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeadlineDto implements Serializable {
    private Long id;
    private Long conferenceId;
    // todo
    private String Date;
    private DeadlineType deadlineType;
}
