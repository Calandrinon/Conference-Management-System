package com.imps.cms.model.converter;

import com.imps.cms.model.Deadline;
import com.imps.cms.model.dto.DeadlineDto;

public class DeadlineConverter {
    public static DeadlineDto convertToDto(Deadline deadline){
        DeadlineDto deadlineDto = new DeadlineDto();
        deadlineDto.setDeadlineType(deadline.getDeadlineType());
        deadlineDto.setDate(deadline.getDate());
        deadlineDto.setId(deadline.getId());
        deadlineDto.setConferenceId(deadline.getConference().getId());
        return deadlineDto;
    }
}
