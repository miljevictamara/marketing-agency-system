package com.bsep.marketingacency.controller;

import io.jsonwebtoken.lang.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/vpn")
public class VPNController {
    private final RestTemplate restTemplate = new RestTemplate();

    private final static Logger logger = LogManager.getLogger(VPNController.class);
    @GetMapping("/call")
    public ResponseEntity<String> callVpnEndpoint() {
        String server = "http://10.13.13.1:3000/";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(server, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Successful VPN call. Response: {}", response.getBody());
                return ResponseEntity.ok("{\"message\": \"" + response.getBody() + "\"}");
            } else {
                logger.error("VPN call failed. Status code: {}", response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body("Request failed");
            }
        } catch (Exception e) {
            logger.error("VPN call failed due to internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
