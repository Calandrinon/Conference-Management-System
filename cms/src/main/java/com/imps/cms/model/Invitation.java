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
@Table(name = "Invitation")
@Builder
public class Invitation {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ReceiverID")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;

    private String status;

    @ManyToOne
    @JoinColumn(name = "SenderID")
    private User sender;

    @Column(name = "text")
    private String text;

    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}
