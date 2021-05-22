package com.imps.cms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
