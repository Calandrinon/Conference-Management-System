package com.CMS.Model;

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
    private String deadlineType;

    public String getDeadlineType() {
        return deadlineType;
    }

    public void setDeadlineType(String deadlineType) {
        this.deadlineType = deadlineType;
    }

    public Deadline(Long id, Conference conference, Date date, String deadlineType) {
        this.id = id;
        this.conference = conference;
        this.date = date;
        this.deadlineType = deadlineType;
    }

}
