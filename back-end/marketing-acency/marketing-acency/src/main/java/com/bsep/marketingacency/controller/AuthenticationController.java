package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.TokenRefreshException;
import com.bsep.marketingacency.dto.*;
import com.bsep.marketingacency.model.*;
import com.bsep.marketingacency.enumerations.RegistrationRequestStatus;
import com.bsep.marketingacency.service.*;
import com.bsep.marketingacency.util.HashUtil;
import com.bsep.marketingacency.util.TokenUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    private final static Logger logger = LogManager.getLogger(AuthenticationController.class);



//    @PostMapping(value = "/login")
//    public ResponseEntity<?> createAuthenticationToken(
//            @RequestBody JwtAuthenticationRequest authenticationRequest,
//            HttpServletResponse response
//    ) {
//        User user = userService.findByMail(authenticationRequest.getMail());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        if (user.getRole().getName().equals("ROLE_EMPLOYEE")) {
//            String gRecaptchaResposnse = authenticationRequest.getCaptchaResponse();
//            try {
//                verifyRecaptcha(gRecaptchaResposnse);
//            } catch (Exception ex) {
//                logger.error("Error verifying ReCaptcha for user with email {}.", authenticationRequest.getMail(), ex);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying ReCaptcha");
//            }
//
//        }
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    authenticationRequest.getMail(), authenticationRequest.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            User authenticatedUser = (User) authentication.getPrincipal();
//
//
//            if (authenticatedUser.isMfa()) {
//                logger.info("User with email {} is using two-factor authentication.", authenticatedUser.getMail());
//                return ResponseEntity.ok().body("");
//            }
//
//            String jwt = tokenUtils.generateToken(authenticatedUser);
//            int expiresIn = tokenUtils.getExpiredIn();
//
//            String refreshJwt = tokenUtils.generateRefreshToken(authenticatedUser);
//            int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();
//
//            UserTokenState tokenState = new UserTokenState(jwt, expiresIn, refreshJwt, refreshExpiresIn);
//            logger.info("User with email {} successfully logged in.", authenticatedUser.getMail());
//            return ResponseEntity.ok(tokenState);
//        } catch (BadCredentialsException ex) {
//            logger.warn("Invalid credentials for user with email {}.", authenticationRequest.getMail());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        } catch (AuthenticationException ex) {
//        logger.error("Authentication failed for user with email {}.", authenticationRequest.getMail(), ex);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
//    }
//    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) {
        try {
            User user = userService.findByMail(authenticationRequest.getMail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getIsBlocked()) {
                logger.warn("User with email {} is blocked.", user.getMail());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is blocked.");
            }
            if (!user.getIsActivated()) {
                logger.warn("User with email {} is not activated.", user.getMail());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not activated.");
            }

            if (user.getRole().getName().equals("ROLE_EMPLOYEE")) {
                String gRecaptchaResponse = authenticationRequest.getCaptchaResponse();
                try {
                    verifyRecaptcha(gRecaptchaResponse);
                } catch (Exception ex) {
                    logger.error("Error verifying ReCaptcha for user with email {}.", authenticationRequest.getMail(), ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying ReCaptcha");
                }
            }

            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getMail(), authenticationRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                User authenticatedUser = (User) authentication.getPrincipal();

                if (authenticatedUser.isMfa()) {
                    logger.info("User with email {} is using two-factor authentication.", authenticatedUser.getMail());
                    return ResponseEntity.ok().body("");
                }

                String jwt = tokenUtils.generateToken(authenticatedUser);
                int expiresIn = tokenUtils.getExpiredIn();

                String refreshJwt = tokenUtils.generateRefreshToken(authenticatedUser);
                int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();

                UserTokenState tokenState = new UserTokenState(jwt, expiresIn, refreshJwt, refreshExpiresIn);
                logger.info("User with email {} successfully logged in.", authenticatedUser.getMail());
                return ResponseEntity.ok(tokenState);
            } catch (BadCredentialsException ex) {
                logger.warn("Invalid credentials for user with email {}.", authenticationRequest.getMail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

        } catch (AuthenticationException ex) {
            logger.error("Authentication failed for user with email {}.", authenticationRequest.getMail(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }


    private void verifyRecaptcha(String gRecaptchaResposnse){
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


    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyTOTP verifyTOTP) {
        try {
            Boolean isValid = userService.verify(verifyTOTP.getMail(), verifyTOTP.getCode());

            if (isValid) {
                User user = userService.findByMail(verifyTOTP.getMail());

                String jwt = tokenUtils.generateToken(user);
                int expiresIn = tokenUtils.getExpiredIn();

                String refreshJwt = tokenUtils.generateRefreshToken(user);
                int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();

                UserTokenState tokenState = new UserTokenState(jwt, expiresIn, refreshJwt, refreshExpiresIn);
                logger.info("User with email {} successfully passed 2fA and logged in.", verifyTOTP.getMail());
                return ResponseEntity.ok(tokenState);
            } else {
                logger.warn("Invalid 2fA code for user with email {}.", verifyTOTP.getMail());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid verification code.");
            }
        } catch (Exception ex) {
            logger.error("Error during 2FA verification for user with email {}.", verifyTOTP.getMail(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during 2FA verification.");
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

//    @PostMapping(value = "/login-tokens")
//    public ResponseEntity<UserTokenState> createAuthenticationTokenWithoutPassword(
//            @RequestBody String mail) {
//
//        User user = userService.findByMail(mail);
//
//        String jwt = tokenUtils.generateToken(user);
//        int expiresIn = tokenUtils.getExpiredIn();
//
//        String refresh_jwt = tokenUtils.generateRefreshToken(user);
//        int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();
//
//        if (!user.getIsBlocked()) {
//            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, refresh_jwt, refreshExpiresIn));
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//
//    }

    @PostMapping(value = "/login-tokens")
    public ResponseEntity<UserTokenState> createAuthenticationTokenWithoutPassword(
            @RequestBody String mail) {

        logger.info("Attempting to generate jwt tokens without password for user with mail {}", mail );
        User user = userService.findByMail(mail);

        if (user != null) {
            if (!user.getIsBlocked() && user.getIsActivated()) {
                String jwt = tokenUtils.generateToken(user);
                int expiresIn = tokenUtils.getExpiredIn();

                String refresh_jwt = tokenUtils.generateRefreshToken(user);
                int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();

                logger.info("User with email {} successfully logged in without password.", mail);

                return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, refresh_jwt, refreshExpiresIn));
            } else {
                if (user.getIsBlocked()) {
                    logger.warn("Passwordless login failed, user with email {} is blocked.", mail);
                } else {
                    logger.warn("Passwordless login failed, user with email {} is not activated..", mail);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/{tokenId}")
    public ResponseEntity<User> findUser(@PathVariable("tokenId") UUID tokenId) {
        try {
            User user = loginTokenService.findUser(tokenId);
            if (user != null) {
                logger.info("User with email {} attempting passwordless login.", user.getMail());
                Boolean isTokenUsed = loginTokenService.checkIfUsed(tokenId);
                if (!isTokenUsed) {
                    return ResponseEntity.ok(user);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing passwordless login.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

//    @PostMapping("/refreshToken")
//    public ResponseEntity<NewAccessToken> refreshtoken(@RequestBody String mail) {
//        User user = userService.findByMail(mail);
//        String jwt = tokenUtils.generateToken(user);
//        int expiresIn = tokenUtils.getExpiredIn();
//
//        NewAccessToken newAccessToken = new NewAccessToken(jwt, expiresIn);
//        if (!user.getIsBlocked()) {
//            return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/refreshToken")
    public ResponseEntity<NewAccessToken> refreshToken(@RequestBody String mail) {
        User user = userService.findByMail(mail);
        if (user != null) {
            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();
            NewAccessToken newAccessToken = new NewAccessToken(jwt, expiresIn);
            if (!user.getIsBlocked()) {
                logger.info("Token refreshed successfully for user with email {}.", mail);
                return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
            } else {
                logger.warn("Token refresh failed, user with email {} is blocked.", mail);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("Token refresh failed, user with email {} not found.", mail);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping(value = "/findByUserId/{id}")
//    public ResponseEntity<User> findById(@PathVariable Long id) {
//        User user = userService.findUserById(id);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping(value = "/findByUserId/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while finding user by ID: {}.", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    @GetMapping(value = "/allIndividuals")
//    @PreAuthorize("hasAuthority('VIEW_ALL_INDIVIDUALS')")
//    public ResponseEntity<List<Client>> getAllIndividuals() {
//        List<Client> individualClients = userService.getAllIndividuals();
//
//        List<Client> filteredClients = individualClients.stream()
//                .filter(client -> client.getIsApproved() == RegistrationRequestStatus.PENDING)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(filteredClients);
//    }

    @GetMapping(value = "/allIndividuals")
    @PreAuthorize("hasAuthority('VIEW_ALL_INDIVIDUALS')")
    public ResponseEntity<List<Client>> getAllIndividuals() {
        try {
            List<Client> individualClients = userService.getAllIndividuals();

            List<Client> filteredClients = individualClients.stream()
                    .filter(client -> client.getIsApproved() == RegistrationRequestStatus.PENDING)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(filteredClients);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all individual clients.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    @GetMapping(value = "/allLegalEntities")
//    @PreAuthorize("hasAuthority('VIEW_ALL_LEGAL_ENTITIES')")
//    public ResponseEntity<List<Client>> getAllLegalEntities() {
//        List<Client> legalEntityClients = userService.getAllLegalEntities();
//
//        List<Client> filteredClients = legalEntityClients.stream()
//                .filter(client -> client.getIsApproved() == RegistrationRequestStatus.PENDING)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(filteredClients);
//    }

    @GetMapping(value = "/allLegalEntities")
    @PreAuthorize("hasAuthority('VIEW_ALL_LEGAL_ENTITIES')")
    public ResponseEntity<List<Client>> getAllLegalEntities() {
        try {
            List<Client> legalEntityClients = userService.getAllLegalEntities();

            List<Client> filteredClients = legalEntityClients.stream()
                    .filter(client -> client.getIsApproved() == RegistrationRequestStatus.PENDING)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(filteredClients);
        } catch (Exception ex) {
            logger.error("An error occurred while fetching legal entity clients.", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
