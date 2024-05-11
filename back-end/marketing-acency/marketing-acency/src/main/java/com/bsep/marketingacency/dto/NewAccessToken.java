package com.bsep.marketingacency.dto;

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
}
