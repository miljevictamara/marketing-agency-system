package com.bsep.marketingacency.dto;

import com.bsep.marketingacency.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDto {
    private long id;
    private String mail;
    private String password;
    private String confirmationPassword;
    private List<Role> roles;
    private Boolean isActivated;
    private Boolean isBlocked;


    public UserDto() {
    }

    public UserDto(long id, String mail, String password, String confirmationPassword, List<Role> roles, Boolean isActivated, Boolean isBlocked) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
        this.roles = roles;
        this.isActivated = isActivated;
        this.isBlocked = isBlocked;
    }
}
