package com.imps.cms.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceDto implements Serializable {
    private Long id;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date submitProposal;

    @Temporal(TemporalType.DATE)
    private Date bidProposals;

    @Temporal(TemporalType.DATE)
    private Date assignPapersToReviewers;

    @Temporal(TemporalType.DATE)
    private Date reviewPapers;

    @Temporal(TemporalType.DATE)
    private Date acceptPapers;

    @Temporal(TemporalType.DATE)
    private Date createSections;

    @Temporal(TemporalType.DATE)
    private Date assignPapersToSections;

    @Temporal(TemporalType.DATE)
    private Date listenerSectionRegistration;
}
