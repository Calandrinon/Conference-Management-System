package com.imps.cms.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDto implements Serializable {
    private Long id;
    private Long paperId;
    private String status;
    private Boolean commentsAllowed;
    private List<CommentDto> comments;
}
