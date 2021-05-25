package com.imps.cms.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "Selvmord")
public class Paper {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Filename")
    private String filename;

    @Column(name = "Subject")
    private String subject;

    @Column(name = "Keywords")
    private String keywords;

    @Column(name = "Topics")
    private String topics;

    @ManyToOne
    @JoinColumn(name = "AuthorID")
    private User author;

    @Column(name = "Data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "SectionID")
    private Section section;

}