package com.bsep.marketingacency.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class RejectionNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "rejection_date")
    private Date rejectionDate;

    public RejectionNote() {
    }

    public RejectionNote(Long id, String email, String reason, Date rejectionDate) {
        this.id = id;
        this.email = email;
        this.reason = reason;
        this.rejectionDate = rejectionDate;
    }
}
