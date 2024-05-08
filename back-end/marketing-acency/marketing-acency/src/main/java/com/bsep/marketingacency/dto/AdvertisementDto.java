package com.bsep.marketingacency.dto;

import com.bsep.marketingacency.model.AdvertisementStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class AdvertisementDto {
    private Long id;

    private long clientId;

    private String slogan;

    private Long duration;

    private String description;

    private Date deadline;

    private Date active_from;

    private Date active_to;

    private String request_description;

    private AdvertisementStatus status;

    public AdvertisementDto() { }

    public AdvertisementDto(Long id, long clientId, String slogan, Long duration, String description, Date deadline, Date active_from, Date active_to, String request_description, AdvertisementStatus status) {
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
