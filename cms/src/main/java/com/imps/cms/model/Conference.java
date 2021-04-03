package com.imps.cms.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Conference")
public class Conference {
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

    @Column(name = "Title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "Deadline")
    private Date deadline;

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Conference() {

    }

    public Conference(Long id, String title, Date deadline){
        this.id = id;
        this.title = title;
        this.deadline = deadline;
    }
}
