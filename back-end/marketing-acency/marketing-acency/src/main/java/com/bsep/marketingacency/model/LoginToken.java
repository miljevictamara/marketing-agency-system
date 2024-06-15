package com.bsep.marketingacency.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@Entity
public class LoginToken {
    @Id
    @GeneratedValue(generator = "pg-uuid")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "is_used")
    private Boolean isUsed;
}
