package com.imps.cms.model;

import javax.persistence.*;

@Entity
@Table(name = "Section")
public class Section {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "SupervisorID")
    private User supervisor;

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
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

    public Section() {

    }

    public Section(String name, User supervisor, Conference conference) {
        this.name = name;
        this.supervisor = supervisor;
        this.conference = conference;
    }
}
