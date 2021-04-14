package com.imps.cms.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "Paper")
public class Paper {
    @Id
    @Column(name = "ID")
    private Long ID;

    @Column(name = "Title")
    private String title;

    @Column(name = "Subject")
    private String subject;

    @Column(name = "Keywords")
    private String keywords;

    @Column(name = "Topics")
    private String topics;

    @ManyToOne
    @JoinColumn(name = "AuthorID")
    private User author;

    @Column(name = "FileName")
    private String filename;

    @ManyToOne
    @JoinColumn(name = "SectionID")
    private Section section;

}