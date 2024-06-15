package com.bsep.marketingacency.service;

import com.bsep.marketingacency.controller.AuthenticationController;
import com.bsep.marketingacency.model.RecaptchaResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {


    @Value("6LcDAe8pAAAAALU_7vCCHftQh0wgcjYOOPKLifoI")
    private String recaptchaSecret;

    @Value("https://www.google.com/recaptcha/api/siteverify")
    private String recaptchaServerUrl;
    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
/*
    public boolean verifyRecaptcha(String recaptchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=" + reCaptchaSecret + "&response=" + recaptchaResponse;
        String fullUrl = url + params;

        RecaptchaResponse response = restTemplate.postForObject(fullUrl, null, RecaptchaResponse.class);

        return response != null && response.isSuccess();
    }*/
private final static Logger logger = LogManager.getLogger(AuthenticationController.class);

    public void verifyRecaptcha(String gRecaptchaResposnse){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", gRecaptchaResposnse);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RecaptchaResponse response = restTemplate.postForObject(recaptchaServerUrl,request, RecaptchaResponse.class);

        logger.info("Verifying recaptcha, Success: {}", response.isSuccess());
//        System.out.println("Success: " + response.isSuccess());
//        System.out.println("Hostname: " + response.getHostname());
//        System.out.println("Challenge Timestamp: " + response.getChallenge_ts());

        if(response.getErrorCodes() != null){
            for(String error : response.getErrorCodes()){
                System.out.println("\t" + error);
            }
        }
}}

