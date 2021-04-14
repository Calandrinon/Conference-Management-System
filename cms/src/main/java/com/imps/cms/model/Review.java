package com.imps.cms.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Review")
public class Review {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ProposalID")
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Score")
    private Long score;

    @Column(name = "Notes")
    private String notes;

}
