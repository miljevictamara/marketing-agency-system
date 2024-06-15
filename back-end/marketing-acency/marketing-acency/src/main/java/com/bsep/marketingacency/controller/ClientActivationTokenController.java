package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientActivationTokenService;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/activation")
public class ClientActivationTokenController {
    @Autowired
    private ClientActivationTokenService clientActivationTokenService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    private Logger logger =  LoggerFactory.getLogger(ClientActivationTokenController.class);

//    @GetMapping("/{tokenId}/{hmac}")
//    public ResponseEntity<User> findUser(@PathVariable UUID tokenId, @PathVariable String hmac) {
//        User user = clientActivationTokenService.findUser(tokenId);
//        Boolean isTokenUsed = clientActivationTokenService.checkIfUsed(tokenId);
//        Boolean isHmacMatches = emailService.verifyHmac("https://localhost:4200/activation/" + tokenId, hmac);
//        if (user != null && !isTokenUsed && isHmacMatches) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }

    @GetMapping("/{tokenId}/{hmac}")
    public ResponseEntity<User> findUser(@PathVariable UUID tokenId, @PathVariable String hmac) {
        try {
            User user = clientService.findUser(tokenId);
            Boolean isTokenUsed = clientActivationTokenService.checkIfUsed(tokenId);
            Boolean isHmacMatches = emailService.verifyHmac("https://localhost:4200/activation/" + tokenId, hmac);
            if (user != null && !isTokenUsed && isHmacMatches) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            logger.error("Error finding user with token {}.", tokenId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
