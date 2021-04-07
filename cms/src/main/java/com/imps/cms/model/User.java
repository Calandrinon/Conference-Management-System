package com.imps.cms.model;

import javax.persistence.*;


@Entity
@Table(name="UserTable")
public class User {
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

    @Column(name = "FullName")
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Column(name = "UserType")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Column(name = "Salt")
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "Email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "Password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {

    }

    @ManyToOne
    @JoinColumn(name = "SectionID")
    private Section section;

    public Section getSection(){return section;}

    public void setSection(Section section){
        this.section = section;
    }

    public User(String fullName, UserType userType, String salt, String email, String password){
        this.fullName = fullName;
        this.userType = userType;
        this.salt = salt;
        this.email = email;
        this.password = password;
    }


}
