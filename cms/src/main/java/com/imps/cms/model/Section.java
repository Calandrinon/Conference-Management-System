package com.imps.cms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Section")
public class Section {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "SupervisorID")
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "ConferenceID")
    private Conference conference;

}
