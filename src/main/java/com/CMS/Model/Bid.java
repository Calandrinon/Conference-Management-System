package com.CMS.Model;

import javax.persistence.*;

@Entity
@Table(name = "Bid")
public class Bid {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "proposalID")
    private Long proposalId;

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    @Column(name = "userID")
    private Long userId;

    public Long getUserID() {
        return userId;
    }

    public void setUserID(Long userID) {
        this.userId = userID;
    }

    @Column(name = "bidStatus")
    private String bidStatus;

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }
}
