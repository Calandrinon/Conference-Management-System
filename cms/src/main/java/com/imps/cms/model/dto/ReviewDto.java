package com.imps.cms.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto implements Serializable {
    private Long id;
    private Long proposalId;
    private Long userId;
    private String notes;
    private Long score;
}
