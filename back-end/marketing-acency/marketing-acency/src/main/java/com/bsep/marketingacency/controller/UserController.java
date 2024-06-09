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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    private final static Logger logger = LogManager.getLogger(UserController.class);

    @PutMapping(value = "/activation/{id}")
    public ResponseEntity<String> updateIsActivated(@PathVariable Long id) {
        try {
            userService.updateIsActivated(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while activating user with ID: {}", HashUtil.hash(id.toString()));
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
            //logger.info("User with email {} found.", mail);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            logger.error("Error while finding user by email: {}", mail);
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
            logger.error("Error while checking if user has appropriate package for email: {}", mail, ex);
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
            logger.error("Error while finding user by email: {}", mail, ex);
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
                logger.info("User with email {} has role: {}", mail, user.getRole().getName());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            logger.error("Error while checking user role for email: {}", mail, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
