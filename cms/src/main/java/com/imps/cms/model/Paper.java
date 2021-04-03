package com.imps.cms.model;


import javax.persistence.*;

@Entity
@Table(name = "Paper")
public class Paper {
    @Id
    @Column(name = "ID")
    private Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Column(name = "Title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    @Column(name = "Subject")
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "Keywords")
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name = "Topics")
    private String topics;

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    @ManyToOne
    @JoinColumn(name = "AuthorID")
    private User author;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(name = "FileName")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @ManyToOne
    @JoinColumn(name = "SectionID")
    private Section section;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Paper() {

    }

    public Paper(Long id, String title, String subject, String keywords, String topics, User author, String filename, Section section) {
        this.ID = id;
        this.title = title;
        this.subject = subject;
        this.keywords = keywords;
        this.topics = topics;
        this.author = author;
        this.filename = filename;
        this.section = section;
    }
}