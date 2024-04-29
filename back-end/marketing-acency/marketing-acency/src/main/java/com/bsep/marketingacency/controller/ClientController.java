package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.model.RejectionNote;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.service.RejectionNoteService;
import com.bsep.marketingacency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RejectionNoteService rejectionNoteService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save-user")
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {

        if (!rejectionNoteService.isUserRejectionExpired(userDto.getMail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User has already been rejected. Please try again later.");
        }else {
            String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\\S+$).{8,}$";
            String password = userDto.getPassword();
            if (!Pattern.matches(passwordRegex, password)) {
                return ResponseEntity.badRequest().body("Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace.");
            }
        }

        if (userService.findByMail(userDto.getMail()) != null) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
        }

        if (!userDto.getPassword().equals(userDto.getConfirmationPassword())) {
            return new ResponseEntity<>("Password and confirmation password do not match", HttpStatus.BAD_REQUEST);
        }

        User savedUser = userService.save(userDto);
        return new ResponseEntity<>("User saved.",HttpStatus.CREATED);

    }
}
