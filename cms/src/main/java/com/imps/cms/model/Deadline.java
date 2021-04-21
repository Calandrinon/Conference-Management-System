package com.imps.cms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "Deadline")
public class Deadline {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ConferenceID")
    private Conference conference;

    @Column(name = "Date")
    private Date date;

    @Column(name = "DeadlineType")
    @Enumerated(EnumType.STRING)
    private DeadlineType deadlineType;

}
