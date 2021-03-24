package com.CMS.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.security.PublicKey;


@Entity
@Table(name="_User_")
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
    private String userType;
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
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

    @Column(name = "Password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(Long id, String fullName, String userType, String salt, String password){
        this.id = id;
        this.fullName = fullName;
        this.userType = userType;
        this.salt = salt;
        this.password = password;
    }
}
