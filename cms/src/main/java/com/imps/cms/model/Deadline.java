package com.imps.cms.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Deadline")
public class Deadline {
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

    @ManyToOne
    @JoinColumn(name = "ConferenceID")
    private Conference conference;

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    @Column(name = "Date")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "DeadlineType")
    @Enumerated(EnumType.STRING)
    private DeadlineType deadlineType;

    public DeadlineType getDeadlineType() {
        return deadlineType;
    }

    public void setDeadlineType(DeadlineType deadlineType) {
        this.deadlineType = deadlineType;
    }

    public Deadline() {

    }

    public Deadline(Conference conference, Date date, DeadlineType deadlineType) {
        this.conference = conference;
        this.date = date;
        this.deadlineType = deadlineType;
    }
}
