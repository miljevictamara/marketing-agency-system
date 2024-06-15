package com.bsep.marketingacency.dto;

public class JwtAuthenticationRequest {
    private String mail;
    private String password;
    private String captchaResponse;


    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String mail, String password,String captchaResponse) {
        this.setMail(mail);
        this.setPassword(password);
        this.setCaptchaResponse(captchaResponse);
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaResponse() {
        return this.captchaResponse;
    }

    public void setCaptchaResponse(String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }
}
