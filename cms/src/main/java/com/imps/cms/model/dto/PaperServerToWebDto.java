package com.imps.cms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperServerToWebDto implements Serializable {
    private Long id;
    private String title;
    private String subject;
    private String topics;
    private String keywords;
    private Long userId;
    private Long sectionId;
    private String status;
    private Long proposalId;
}
