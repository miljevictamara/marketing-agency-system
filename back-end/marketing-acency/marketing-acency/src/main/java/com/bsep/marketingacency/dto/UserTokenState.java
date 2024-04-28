package com.bsep.marketingacency.dto;

public class UserTokenState {
    private String accessToken;
    private Long accessExpiresIn;

    private String refreshToken;
    private Long refreshExpiresIn;

    public UserTokenState() {
        this.accessToken = null;
        this.accessExpiresIn = null;
        this.refreshToken = null;
        this.refreshExpiresIn = null;
    }

    public UserTokenState(String accessToken, long expiresIn,String refreshToken, long refreshExpiresIn) {
        this.accessToken = accessToken;
        this.accessExpiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getAccessExpiresIn() {
        return accessExpiresIn;
    }

    public void setAccessExpiresIn(Long accessExpiresIn) {
        this.accessExpiresIn = accessExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(Long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }
}
