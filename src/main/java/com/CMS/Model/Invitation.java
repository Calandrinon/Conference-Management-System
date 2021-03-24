package com.CMS.Model;

import javax.persistence.*;

@Entity
@Table(name = "Invitation")
class Invitation {
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

    @Column(name = "Mail")
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @ManyToOne
    @JoinColumn(name = "senderID")
    private User sender;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Column(name = "text")
    private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Invitation(Long id, String mail, User sender, String text)
    {
        this.id = id;
        this.mail = mail;
        this.sender = sender;
        this.text = text;
    }
}
