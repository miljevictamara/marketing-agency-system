package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.model.User;
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

    @PutMapping(value = "/activation/{id}")
    public ResponseEntity<String> updateIsActivated(@PathVariable Long id){
        userService.updateIsActivated(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/findByEmail/{mail}")
    public ResponseEntity<User> findByMail(@PathVariable String mail) {
        User user = userService.findByMail(mail);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
