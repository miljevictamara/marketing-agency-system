package com.bsep.marketingacency.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
@Getter
@Setter
public class RejectionNoteDto {
    private Long id;
    private String email;
    private String reason;
    private Date rejectionDate;

    public RejectionNoteDto() {
    }

    public RejectionNoteDto(Long id, String email, String reason, Date rejectionDate) {
        this.id = id;
        this.email = email;
        this.reason = reason;
        this.rejectionDate = rejectionDate;
    }
}
