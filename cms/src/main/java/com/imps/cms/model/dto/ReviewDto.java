package com.imps.cms.model.dto;


import com.imps.cms.model.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto implements Serializable {
    private Long id;
    private Long proposalId;
    private Long userId;
    private String notes;
    private Long score;
    private ReviewStatus reviewStatus;
}
