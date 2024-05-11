package com.bsep.marketingacency.dto;

public class AdministratorDto {
    private Long Id;

    private String firstName;

    private String address;

    private String city;

    private String country;

    private String lastName;
    private String phoneNumber;

    private Long userId;

    public AdministratorDto() { }

    public AdministratorDto(Long id, String firstName, String lastName, String address, String city, String country, String phoneNumber, Long userId) {
        Id = id;
        this.firstName = firstName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    public Long getId() {
        return Id;
    }

    public String getFirstName() {
        return firstName;
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

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
