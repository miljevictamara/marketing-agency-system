package com.bsep.marketingacency.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Package {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "visits_number")
    private Integer visitsNumber;

    @Column(name = "price")
    private Double price;

    public Package() {
    }

    public Package(Long id, String name, Integer visitsNumber, Double price) {
        this.id = id;
        this.name = name;
        this.visitsNumber = visitsNumber;
        this.price = price;
    }
}
