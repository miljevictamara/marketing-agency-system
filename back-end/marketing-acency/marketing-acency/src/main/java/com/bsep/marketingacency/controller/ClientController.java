package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.dto.RejectionNoteDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.model.*;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.service.EmailService;
import com.bsep.marketingacency.model.Package;
import com.bsep.marketingacency.model.RejectionNote;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.service.PackageService;
import com.bsep.marketingacency.service.RejectionNoteService;
import com.bsep.marketingacency.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/client")
public class ClientController {
    private Logger logger =  LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private RejectionNoteService rejectionNoteService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


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


        String emailRegex = "^(.+)@(.+)$";
        String email = userDto.getMail();
        if (!Pattern.matches(emailRegex, email) || userService.findByMail(userDto.getMail()) != null) {
            return new ResponseEntity<>("Invalid email format or email is already in use.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!userDto.getPassword().equals(userDto.getConfirmationPassword())) {
            return new ResponseEntity<>("Password and confirmation password do not match", HttpStatus.CONFLICT);
        }



        User savedUser = userService.save(userDto);
        return new ResponseEntity<>("User saved.",HttpStatus.CREATED);

    }

    @PostMapping(value = "/save-employee-user")
    public ResponseEntity<String> saveEmployeeUser(@RequestBody UserDto userDto) {
        User savedUser = userService.saveEmployeeUser(userDto);
        return new ResponseEntity<>("User saved.",HttpStatus.CREATED);
    }

    @PostMapping(value = "/save-admin-user")
    public ResponseEntity<String> saveAdminUser(@RequestBody UserDto userDto) {
        User savedUser = userService.saveAdminUser(userDto);
        return new ResponseEntity<>("User saved.",HttpStatus.CREATED);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody ClientDto clientDto) {

        Client savedClient = clientService.save(clientDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/approve-registration-request/{id}")
    public ResponseEntity<String> register(@PathVariable  Long id){
        ClientActivationToken token = clientService.approveRegistrationRequest(id);
        Client client = clientService.findById(id);
        try {
            System.out.println("Thread id: " + Thread.currentThread().getId());
            emailService.sendRegistrationApprovalAsync(client, token);
        }catch( Exception e ){
            logger.info("Greska prilikom slanja emaila: " + e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/reject-registration-request/{id}")
    public ResponseEntity<String> register(@PathVariable  Long id, @RequestParam String reason){
        clientService.rejectRegistrationRequest(id, reason);
        Client client = clientService.findById(id);
        User user = client.getUser();
        try {
            System.out.println("Thread id: " + Thread.currentThread().getId());
            emailService.sendRegistrationRejectionAsync(client, reason);
            clientService.delete(client);
            userService.delete(user);
        }catch( Exception e ){
            logger.info("Greska prilikom slanja emaila: " + e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<ClientDto> getClientByUserId(@PathVariable Long userId) {
        Client client = clientService.getClientByUserId(userId);
        if (client != null) {
            ClientDto clientDto = new ClientDto(
                    client.getId(),
                    client.getUser(),
                    client.getType(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getCompanyName(),
                    client.getPib(),
                    client.getClientPackage(),
                    client.getPhoneNumber(),
                    client.getAddress(),
                    client.getCity(),
                    client.getCountry(),
                    client.getIsApproved()
            );
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto) {
        Client updatedClient = new Client(
                clientDto.getId(),
                clientDto.getUser(),
                clientDto.getType(),
                clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getCompanyName(),
                clientDto.getPib(),
                clientDto.getClientPackage(),
                clientDto.getPhoneNumber(),
                clientDto.getAddress(),
                clientDto.getCity(),
                clientDto.getCountry(),
                clientDto.getIsApproved()
        );

        Client updated = clientService.updateClient(updatedClient);

        if (updated != null) {
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
