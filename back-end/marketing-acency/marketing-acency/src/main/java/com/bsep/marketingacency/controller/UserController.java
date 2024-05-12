package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @PutMapping(value = "/activation/{id}")
    public ResponseEntity<String> updateIsActivated(@PathVariable Long id){
        userService.updateIsActivated(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{mail}")
    public ResponseEntity<User> findUserByMail(@PathVariable String mail){
        User user = userService.findByMail(mail);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/package/{mail}")
    public ResponseEntity<Boolean> checkIfUserHasAppropriatePackage(@PathVariable String mail){
        User user = userService.findByMail(mail);
        if(user == null && !clientService.checkIfClientCanLoginWithoutPassword(mail)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(true);
    }
}
