package com.imps.cms.model;


import javax.persistence.*;

@Entity
@Table(name = "UserRole")
public class UserRole {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ConferenceID")
    private Conference conference;

    @Enumerated(EnumType.STRING)
    private UserType userType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserRole(){}

    public UserRole(User user, Conference conference, UserType userType){
        this.user = user;
        this.conference = conference;
        this.userType = userType;
    }
}
