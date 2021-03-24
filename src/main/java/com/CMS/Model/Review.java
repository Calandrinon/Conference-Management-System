package com.CMS.Model;


import javax.persistence.*;

@Entity
@Table(name = "Review")
public class Review {
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
    @JoinColumn(name = "proposalID")
    private Proposal proposal;

    public Proposal getProposal(){
        return proposal;
    }

    public void setProposal(Proposal proposal){
        this.proposal = proposal;
    }

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }


    @Column(name = "Score")
    private Long score;

    public Long getScore(){
        return score;
    }

    public void setScore(Long score){
        this.score = score;
    }

    @Column(name = "Notes")
    private String notes;

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }
}