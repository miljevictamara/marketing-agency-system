package com.bsep.marketingacency.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ClientActivationToken {
    @Id
    @GeneratedValue(generator = "pg-uuid")
    private UUID id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "is_used")
    private Boolean isUsed;

    public ClientActivationToken() {
    }

    public ClientActivationToken(UUID id, User user, Date creationDate, Integer duration, Boolean isUsed) {
        this.id = id;
        this.user = user;
        this.creationDate = creationDate;
        this.duration = duration;
        this.isUsed = isUsed;
    }
}
