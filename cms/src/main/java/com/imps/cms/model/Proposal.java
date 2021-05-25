package com.imps.cms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Proposal")
public class Proposal {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PaperID")
    private Paper paper;

    @Column(name = "Status")
    private String status;

    private Boolean commentsAllowed;

    @OneToMany(mappedBy = "proposal")
    private List<Comment> comments;
}
