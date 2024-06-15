package com.bsep.marketingacency.model;

import com.bsep.marketingacency.service.AESConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Administrator {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Administrator() {

    }

    public Administrator(Long id, String firstName, String lastName, String address, String city, String country, String phoneNumber, User user) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.user = user;
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

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User getUser() {
        return user;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoneNumber(SecretKey key) throws IllegalBlockSizeException, BadPaddingException {
        return AESConverter.decryptFromString(key, this.phoneNumber);
    }

    public String getAddress(SecretKey key) throws IllegalBlockSizeException, BadPaddingException {
        return AESConverter.decryptFromString(key, this.address);
    }

    public void setPhoneNumber(String phoneNumber, SecretKey key) {
        this.phoneNumber = AESConverter.encryptToString(key, phoneNumber);
    }

    public void setAddress(String address, SecretKey key) {
        this.address = AESConverter.encryptToString(key, address);
    }

    public String getFirstName(SecretKey key) throws IllegalBlockSizeException, BadPaddingException {
        return AESConverter.decryptFromString(key, this.firstName);
    }

    public String getLastName(SecretKey key) throws IllegalBlockSizeException, BadPaddingException {
        return AESConverter.decryptFromString(key, this.lastName);
    }

    public void setFirstName(String firstName, SecretKey key) {
        this.firstName = AESConverter.encryptToString(key, firstName);
    }

    public void setLastName(String lastName, SecretKey key) {
        this.lastName = AESConverter.encryptToString(key, lastName);
    }


}
