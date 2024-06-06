package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.TokenRefreshException;
import com.bsep.marketingacency.dto.*;
import com.bsep.marketingacency.model.*;
import com.bsep.marketingacency.enumerations.RegistrationRequestStatus;
import com.bsep.marketingacency.service.*;
import com.bsep.marketingacency.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:4200")
public class AuthenticationController {
    @Value("6LcDAe8pAAAAALU_7vCCHftQh0wgcjYOOPKLifoI")
    private String recaptchaSecret;

    @Value("https://www.google.com/recaptcha/api/siteverify")
    private String recaptchaServerUrl;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private RecaptchaService recaptchaService;


    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) {
        User user = userService.findByMail(authenticationRequest.getMail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (user.getRole().getName().equals("ROLE_EMPLOYEE")) {
            String gRecaptchaResposnse = authenticationRequest.getCaptchaResponse();
            verifyRecaptcha(gRecaptchaResposnse);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getMail(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authenticatedUser = (User) authentication.getPrincipal();

        if (authenticatedUser.getIsBlocked() || !authenticatedUser.getIsActivated()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (authenticatedUser.isMfa()) {
            return ResponseEntity.ok().body("");
        }

        String jwt = tokenUtils.generateToken(authenticatedUser);
        int expiresIn = tokenUtils.getExpiredIn();

        String refreshJwt = tokenUtils.generateRefreshToken(authenticatedUser);
        int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();

        UserTokenState tokenState = new UserTokenState(jwt, expiresIn, refreshJwt, refreshExpiresIn);
        return ResponseEntity.ok(tokenState);
    }
    private void verifyRecaptcha(String gRecaptchaResposnse){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaSecret);
        map.add("response", gRecaptchaResposnse);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RecaptchaResponse response = restTemplate.postForObject(recaptchaServerUrl,request, RecaptchaResponse.class);

        System.out.println("Success: " + response.isSuccess());
        System.out.println("Hostname: " + response.getHostname());
        System.out.println("Challenge Timestamp: " + response.getChallenge_ts());

        if(response.getErrorCodes() != null){
            for(String error : response.getErrorCodes()){
                System.out.println("\t" + error);
            }
        }


    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyTOTP verifyTOTP) {
        Boolean isValid = userService.verify(verifyTOTP.getMail(), verifyTOTP.getCode());

        if (isValid) {
            User user = userService.findByMail(verifyTOTP.getMail());

            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();

            String refreshJwt = tokenUtils.generateRefreshToken(user);
            int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();

            UserTokenState tokenState = new UserTokenState(jwt, expiresIn, refreshJwt, refreshExpiresIn);
            return ResponseEntity.ok(tokenState);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid verification code.");
        }
    }


    @PostMapping(value = "/passwordless-login")
    public ResponseEntity<String> sendLoginToken(
            @RequestBody String mail) throws InterruptedException {
        if(!clientService.checkIfClientCanLoginWithoutPassword(mail)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        emailService.sendLoginToken(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login-tokens")
    public ResponseEntity<UserTokenState> createAuthenticationTokenWithoutPassword(
            @RequestBody String mail) {

        User user = userService.findByMail(mail);

        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        String refresh_jwt = tokenUtils.generateRefreshToken(user);
        int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();

        if (!user.getIsBlocked()) {
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, refresh_jwt, refreshExpiresIn));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @GetMapping("/{tokenId}")
    public ResponseEntity<User> findUser(@PathVariable("tokenId") UUID tokenId) {
        User user = loginTokenService.findUser(tokenId);
        Boolean isTokenUsed = loginTokenService.checkIfUsed(tokenId);
        if (user != null && !isTokenUsed) {

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // pristup: svi
    @GetMapping(value = "/findByEmail/{mail}")
    public ResponseEntity<User> findByMail(@PathVariable String mail){
        User user = userService.findByMail(mail);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<NewAccessToken> refreshtoken(@RequestBody String mail) {
        User user = userService.findByMail(mail);
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        NewAccessToken newAccessToken = new NewAccessToken(jwt, expiresIn);
        if (!user.getIsBlocked()) {
            return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/findByUserId/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/allIndividuals")
    @PreAuthorize("hasAuthority('VIEW_ALL_INDIVIDUALS')")
    public ResponseEntity<List<Client>> getAllIndividuals() {
        List<Client> individualClients = userService.getAllIndividuals();

        List<Client> filteredClients = individualClients.stream()
                .filter(client -> client.getIsApproved() == RegistrationRequestStatus.PENDING)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredClients);
    }

    @GetMapping(value = "/allLegalEntities")
    @PreAuthorize("hasAuthority('VIEW_ALL_LEGAL_ENTITIES')")
    public ResponseEntity<List<Client>> getAllLegalEntities() {
        List<Client> legalEntityClients = userService.getAllLegalEntities();

        List<Client> filteredClients = legalEntityClients.stream()
                .filter(client -> client.getIsApproved() == RegistrationRequestStatus.PENDING)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredClients);
    }

}
