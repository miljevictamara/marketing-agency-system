package com.bsep.marketingacency.dto;

import com.bsep.marketingacency.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
@Getter
@Setter
public class ClientActivationTokenDto {
    private UUID id;
    private User user;
    private Date creationDate;
    private Integer duration;

    public ClientActivationTokenDto() {
    }

    public ClientActivationTokenDto(UUID id, User user, Date creationDate, Integer duration) {
        this.id = id;
        this.user = user;
        this.creationDate = creationDate;
        this.duration = duration;
    }
}
