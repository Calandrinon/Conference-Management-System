package com.CMS.Model;

import javax.persistence.*;

@Entity
@Table(name = "AdminUser")
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

    public AdminUser(Long id, String fullName, String userType, String salt, String password, Conference conference) {
        super(id, fullName, userType, salt, password);
        this.conference = conference;
    }
}
