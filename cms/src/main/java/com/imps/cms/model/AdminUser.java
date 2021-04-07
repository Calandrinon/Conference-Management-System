package com.imps.cms.model;

import javax.persistence.*;

@Entity
public class AdminUser extends User{
    @ManyToOne
    @JoinColumn(name = "ConferenceID")
    private Conference conference;

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public AdminUser() {

    }

    public AdminUser(String fullName, UserType userType, String salt, String password, String email, Conference conference) {
        super(fullName, userType, salt, email, password);
        this.conference = conference;
    }
}
