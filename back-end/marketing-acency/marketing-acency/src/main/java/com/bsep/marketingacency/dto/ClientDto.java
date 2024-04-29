package com.bsep.marketingacency.dto;

import com.bsep.marketingacency.enumerations.ClientType;
import com.bsep.marketingacency.enumerations.RegistrationRequestStatus;
import com.bsep.marketingacency.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
@Getter
@Setter
public class ClientDto {
    private Long Id;
    private User user;
    private ClientType type;
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String companyName;
    @Nullable
    private Integer pib;
    private String address;
    private String city;
    private String country;
    private RegistrationRequestStatus isApproved;

    public ClientDto() {
    }

    public ClientDto(Long id, User user, ClientType type, @Nullable String firstName, @Nullable String lastName, @Nullable String companyName, @Nullable Integer pib, String address, String city, String country, RegistrationRequestStatus isApproved) {
        Id = id;
        this.user = user;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.pib = pib;
        this.address = address;
        this.city = city;
        this.country = country;
        this.isApproved = isApproved;
    }
}
