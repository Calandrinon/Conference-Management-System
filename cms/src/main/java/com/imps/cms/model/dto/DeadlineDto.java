package com.imps.cms.model.dto;

import com.imps.cms.model.DeadlineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeadlineDto implements Serializable {
    private Long id;
    private Long conferenceId;
    // todo
    private Date Date;
    private DeadlineType deadlineType;
}
