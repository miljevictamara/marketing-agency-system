package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientActivationTokenService;
import com.bsep.marketingacency.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/activation")
public class ClientActivationTokenController {
    @Autowired
    private ClientActivationTokenService clientActivationTokenService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/{tokenId}/{hmac}")
    public ResponseEntity<User> findUser(@PathVariable("tokenId") UUID tokenId, @PathVariable("hmac") String hmac) {
        User user = clientActivationTokenService.findUser(tokenId);
        Boolean isTokenUsed = clientActivationTokenService.checkIfUsed(tokenId);
        Boolean isHmacMatches = emailService.verifyHmac("https://localhost:4200/activation/" + tokenId, hmac);
        if (user != null && !isTokenUsed && isHmacMatches) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
