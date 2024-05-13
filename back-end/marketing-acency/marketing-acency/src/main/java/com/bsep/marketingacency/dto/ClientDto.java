package com.bsep.marketingacency.dto;

import com.bsep.marketingacency.deserilizer.UserDeserializer;
import com.bsep.marketingacency.enumerations.ClientType;
import com.bsep.marketingacency.enumerations.RegistrationRequestStatus;
import com.bsep.marketingacency.model.Package;
import com.bsep.marketingacency.model.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
@Getter
@Setter
public class ClientDto {
    private Long Id;
    @JsonDeserialize(using = UserDeserializer.class)
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
    private Package clientPackage;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private RegistrationRequestStatus isApproved;

    public ClientDto() {
    }

    public ClientDto(Long id, User user, ClientType type, @Nullable String firstName, @Nullable String lastName, @Nullable String companyName, @Nullable Integer pib, Package clientPackage, String phoneNumber, String address, String city, String country, RegistrationRequestStatus isApproved) {
        Id = id;
        this.user = user;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.pib = pib;
        this.clientPackage = clientPackage;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.isApproved = isApproved;
    }

    public Long getId() {
        return Id;
    }

    public User getUser() {
        return user;
    }

    public ClientType getType() {
        return type;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getCompanyName() {
        return companyName;
    }

    @Nullable
    public Integer getPib() {
        return pib;
    }

    public Package getClientPackage() {
        return clientPackage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public RegistrationRequestStatus getIsApproved() {
        return isApproved;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    public void setCompanyName(@Nullable String companyName) {
        this.companyName = companyName;
    }

    public void setPib(@Nullable Integer pib) {
        this.pib = pib;
    }

    public void setClientPackage(Package clientPackage) {
        this.clientPackage = clientPackage;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIsApproved(RegistrationRequestStatus isApproved) {
        this.isApproved = isApproved;
    }
}
