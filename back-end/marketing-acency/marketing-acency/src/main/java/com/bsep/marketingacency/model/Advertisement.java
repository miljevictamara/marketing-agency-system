package com.bsep.marketingacency.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private long clientId;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "duration")
    private Long duration; // u sekundama

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "active_from")
    private Date active_from;

    @Column(name = "active_to")
    private Date active_to;

    @Column(name = "request_description")
    private String request_description;

    @Column(name = "status")
    private AdvertisementStatus status;

    public Advertisement() { }

    public Advertisement(Long id, long clientId, String slogan, Long duration, String description, Date deadline, Date active_from, Date active_to, String request_description, AdvertisementStatus status) {
        this.id = id;
        this.clientId = clientId;
        this.slogan = slogan;
        this.duration = duration;
        this.description = description;
        this.deadline = deadline;
        this.active_from = active_from;
        this.active_to = active_to;
        this.request_description = request_description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public String getSlogan() {
        return slogan;
    }

    public Long getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getActive_from() {
        return active_from;
    }

    public Date getActive_to() {
        return active_to;
    }

    public String getRequest_description() {
        return request_description;
    }

    public AdvertisementStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setActive_from(Date active_from) {
        this.active_from = active_from;
    }

    public void setActive_to(Date active_to) {
        this.active_to = active_to;
    }

    public void setRequest_description(String request_description) {
        this.request_description = request_description;
    }

    public void setStatus(AdvertisementStatus status) {
        this.status = status;
    }
}
