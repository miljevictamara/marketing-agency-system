package com.bsep.marketingacency.model;

import javax.persistence.*;

@Entity
public class Client {

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
