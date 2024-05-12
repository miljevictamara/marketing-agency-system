package com.bsep.marketingacency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAccessToken {
    private String accessToken;
    private Long accessExpiresIn;


    public NewAccessToken() {
        this.accessToken = null;
        this.accessExpiresIn = null;
    }

    public NewAccessToken(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.accessExpiresIn = expiresIn;
    }
}
