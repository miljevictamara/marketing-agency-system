package com.bsep.marketingacency.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
