package com.bsep.marketingacency.dto;

public class EmployeeDto {
    private Long Id;

    private String firstName;

    private String lastName;
    private String phoneNumber;
    private String address;
    private Long userId;

    public EmployeeDto(Long id, String firstName, String lastName, String phoneNumber, String address, Long userId) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userId = userId;
    }

    public Long getId() {
        return Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

