package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.TokenRefreshException;
import com.bsep.marketingacency.dto.JwtAuthenticationRequest;
import com.bsep.marketingacency.dto.NewAccessToken;
import com.bsep.marketingacency.dto.UserTokenState;
import com.bsep.marketingacency.model.TokenRefreshRequest;
import com.bsep.marketingacency.model.TokenRefreshResponse;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.service.EmailService;
import com.bsep.marketingacency.service.LoginTokenService;
import com.bsep.marketingacency.service.RefreshTokenService;
import com.bsep.marketingacency.service.UserService;
import com.bsep.marketingacency.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import javax.validation.Valid;
import com.bsep.marketingacency.model.RefreshToken;
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:4200")
public class AuthenticationController {
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

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping(value = "/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getMail(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();

        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        String refresh_jwt = tokenUtils.generateRefreshToken(user);
        int refreshExpiresIn = tokenUtils.getRefreshExpiredIn();


        // Vrati token kao odgovor na uspesnu autentifikaciju
        if(!user.getIsBlocked() && user.getIsActivated()){
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, refresh_jwt, refreshExpiresIn));
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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


        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, refresh_jwt, refreshExpiresIn));

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
}
