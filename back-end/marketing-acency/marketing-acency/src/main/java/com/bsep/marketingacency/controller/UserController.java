package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.service.UserService;
import com.bsep.marketingacency.util.HashUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bsep.marketingacency.model.*;
import com.bsep.marketingacency.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private ClientActivationTokenService clientActivationTokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RejectionNoteService rejectionNoteService;

    @Autowired
    private ClientService clientService;

    private final static Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping(value = "/activation/{id}")
    public ResponseEntity<String> updateIsActivated(@PathVariable Long id) {
        try {
            userService.updateIsActivated(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while activating user with ID: {}.", HashUtil.hash(id.toString()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{mail}")
    public ResponseEntity<User> findUserByMail(@PathVariable String mail){
        try {
            User user = userService.findByMail(mail);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            logger.error("Error while finding user by email: {}.", mail);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping(value = "/package/{mail}")
//    public ResponseEntity<Boolean> checkIfUserHasAppropriatePackage(@PathVariable String mail){
//        User user = userService.findByMail(mail);
//        if(user == null && !clientService.checkIfClientCanLoginWithoutPassword(mail)){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(true);
//    }

    @GetMapping(value = "/package/{mail}")
    public ResponseEntity<Boolean> checkIfUserHasAppropriatePackage(@PathVariable String mail){
        try {
            User user = userService.findByMail(mail);
            if(user == null && !clientService.checkIfClientCanLoginWithoutPassword(mail)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(true);
        } catch (Exception ex) {
            logger.error("Error while checking if client {} has appropriate package.", mail);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/findByEmail/{mail}")
//    public ResponseEntity<User> findByMail(@PathVariable String mail) {
//        User user = userService.findByMail(mail);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping(value = "/findByEmail/{mail}")
    public ResponseEntity<User> findByMail(@PathVariable String mail) {
        try {
            User user = userService.findByMail(mail);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            logger.error("Error while finding user by email: {}.", mail, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    @GetMapping("/check-role/{mail}")
//    public ResponseEntity<Map<String, String>> checkUserRole(@PathVariable String mail) {
//        User user = userService.findByMail(mail);
//        if (user != null) {
//            Map<String, String> response = new HashMap<>();
//            response.put("role", user.getRole().getName());
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/check-role/{mail}")
    public ResponseEntity<Map<String, String>> checkUserRole(@PathVariable String mail) {
        try {
            User user = userService.findByMail(mail);
            if (user != null) {
                Map<String, String> response = new HashMap<>();
                response.put("role", user.getRole().getName());
                logger.info("User {} has role: {}.", mail, user.getRole().getName());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            logger.error("Error while checking user {} role.", mail);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping(value = "/{userId}/{password}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId, @PathVariable String password) throws IOException {
        User user = userService.findUserById(userId);
        logger.info("{}  wants to be deleted.", user.getMail());
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Incorrect password for client {}.", user.getMail());
            return new ResponseEntity<>("Incorrect password!", HttpStatus.CONFLICT);
        }

        clientService.deleteClient(userId);
        loginTokenService.delete(userId);
        clientActivationTokenService.delete(userId);
        rejectionNoteService.delete(userId);
        userService.deleteUser(userId);

        logger.info("Client {} and all associated entities successfully deleted from the system.", user.getMail());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(value = "/keystore/{email}")
    public ResponseEntity<String> deleteKeystore(@PathVariable String email) throws IOException {
        if (email == null) {
            return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
        }

        String keystorePath = email + ".jks";
        Files.deleteIfExists(Paths.get(keystorePath));

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('BLOCK_USER')")
    @PutMapping(value = "/blocking/{id}")
    public ResponseEntity<String> updateIsBlocked(@PathVariable Long id) {
        try {
            userService.updateIsBlocked(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while blocking user with ID: {}", HashUtil.hash(id.toString()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
