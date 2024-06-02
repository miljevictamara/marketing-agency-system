package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    @Value("$6LcDAe8pAAAAALU_7vCCHftQh0wgcjYOOPKLifoI")
    private String reCaptchaSecret;

    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyRecaptcha(String recaptchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=" + reCaptchaSecret + "&response=" + recaptchaResponse;
        String fullUrl = url + params;

        RecaptchaResponse response = restTemplate.postForObject(fullUrl, null, RecaptchaResponse.class);

        return response != null && response.isSuccess();
    }
}

