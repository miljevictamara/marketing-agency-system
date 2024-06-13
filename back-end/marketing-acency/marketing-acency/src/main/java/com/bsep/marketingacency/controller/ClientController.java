package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.*;
import com.bsep.marketingacency.keystores.KeyStoreReader;
import com.bsep.marketingacency.keystores.KeyStoreWriter;
import com.bsep.marketingacency.model.*;
import com.bsep.marketingacency.repository.KeyStoreAccessRepository;
import com.bsep.marketingacency.service.*;
import com.bsep.marketingacency.model.Package;
import com.bsep.marketingacency.model.RejectionNote;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.util.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
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

    @Autowired
    private PackageService packageService;

    @Autowired
    private TOTPManager totpManager;


    @Autowired
    private KeyStoreAccessRepository keyStoreAccessRepository;
//    @PostMapping(value = "/save-user")
//    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
//
//        if (!rejectionNoteService.isUserRejectionExpired(userDto.getMail())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User has already been rejected. Please try again later.");
//        }else {
//            String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\\S+$).{8,}$";
//            String password = userDto.getPassword();
//            if (!Pattern.matches(passwordRegex, password)) {
//                return ResponseEntity.badRequest().body("Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace.");
//            }
//        }
//
//
//        String emailRegex = "^(.+)@(.+)$";
//        String email = userDto.getMail();
//        if (!Pattern.matches(emailRegex, email) || userService.findByMail(userDto.getMail()) != null) {
//            return new ResponseEntity<>("Invalid email format or email is already in use.", HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//
//        if (!userDto.getPassword().equals(userDto.getConfirmationPassword())) {
//            return new ResponseEntity<>("Password and confirmation password do not match", HttpStatus.CONFLICT);
//        }
//        //dodato za dvofaktorku, kao i dva nova polja u user i userDto - mfa, secret
//        if(userDto.getMfa()) {
//            userDto.setSecret(totpManager.generateSecret());
//        }
//
//        User savedUser = userService.save(userDto);
//        return new ResponseEntity<>("User saved.",HttpStatus.CREATED);
//
//    }

    @PostMapping(value = "/save-user")
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
        try {
            if (!rejectionNoteService.isUserRejectionExpired(userDto.getMail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User has already been rejected. Please try again later.");
            }

            String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+.=])(?=\\S+$).{8,}$";
            String password = userDto.getPassword();
            if (!Pattern.matches(passwordRegex, password)) {
                logger.warn("Password does not meet complexity requirements for user with email {}.", userDto.getMail());
                return ResponseEntity.badRequest().body("Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace.");
            }

            String emailRegex = "^(.+)@(.+)$";
            String email = userDto.getMail();
            if (!Pattern.matches(emailRegex, email) || userService.findByMail(userDto.getMail()) != null) {
                logger.warn("Invalid email format or email is already in use : {}.", userDto.getMail());
                return new ResponseEntity<>("Invalid email format or email is already in use.", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            if (!userDto.getPassword().equals(userDto.getConfirmationPassword())) {
                logger.warn("Password and confirmation password do not match for user with email {}.", userDto.getMail());
                return new ResponseEntity<>("Password and confirmation password do not match", HttpStatus.CONFLICT);
            }

            if (userDto.getMfa()) {
                userDto.setSecret(totpManager.generateSecret());
                logger.info("Secret generated for user with email {}.", userDto.getMail());
            }

            User savedUser = userService.save(userDto);
            logger.info("User with email {} successfully saved.", userDto.getMail());
            return new ResponseEntity<>("User saved.", HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("An error occurred while saving user with email {}.", userDto.getMail(), ex);
            return new ResponseEntity<>("An error occurred while saving the user. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ClientRegistrationResponse> register(@RequestBody ClientDto clientDto) {
//        Client savedClient = clientService.save(clientDto);
//        boolean isMfaEnabled = savedClient.getUser().isMfa();
//        String secretImageUri = isMfaEnabled ? totpManager.getUriForImage(savedClient.getUser().getSecret()) : null;
//        ClientRegistrationResponse response = new ClientRegistrationResponse(isMfaEnabled, secretImageUri);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientRegistrationResponse> register(@RequestBody ClientDto clientDto) {
        try {
            SecretKey secretKey = KeyStoreWriter.generateSecretKey("AES", 256);
            KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
            keyStoreWriter.loadKeyStore(null, "marketing-agency".toCharArray());
            keyStoreWriter.writeSecretKey(clientDto.getUser(), secretKey, "marketing-agency".toCharArray());
            keyStoreWriter.saveKeyStore(clientDto.getUser() + ".jks", "marketing-agency".toCharArray());

            Client savedClient = clientService.save(clientDto, secretKey);
            boolean isMfaEnabled = savedClient.getUser().isMfa();
            String secretImageUri = isMfaEnabled ? totpManager.getUriForImage(savedClient.getUser().getSecret()) : null;
            ClientRegistrationResponse response = new ClientRegistrationResponse(isMfaEnabled, secretImageUri);
            logger.info("Client with email {} successfully sent registration request.", clientDto.getUser());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering client with email {}: {}", clientDto.getUser(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // pristup: Administrator
//    @PostMapping(value = "/save-employee-user")
//    @PreAuthorize("hasAuthority('SAVE_EMPLOYEE_USER')")
//    public ResponseEntity<String> saveEmployeeUser(@RequestBody UserDto userDto) {
//        User savedUser = userService.saveEmployeeUser(userDto);
//        return new ResponseEntity<>("User saved.",HttpStatus.CREATED);
//    }

    @PostMapping(value = "/save-employee-user")
    @PreAuthorize("hasAuthority('SAVE_EMPLOYEE_USER')")
    public ResponseEntity<String> saveEmployeeUser(@RequestBody UserDto userDto) {
        try {
            User savedUser = userService.saveEmployeeUser(userDto);
            logger.info("Employee user with email {} successfully saved.", userDto.getMail());
            return new ResponseEntity<>("User saved.", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving employee user with email {}: {}", userDto.getMail(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//     pristup: Administrator
//    @PostMapping(value = "/save-admin-user")
//    @PreAuthorize("hasAuthority('SAVE_ADMIN_USER')")
//    public ResponseEntity<String> saveAdminUser(@RequestBody UserDto userDto) {
//        User savedUser = userService.saveAdminUser(userDto);
//        return new ResponseEntity<>("User saved.",HttpStatus.CREATED);
//    }

    @PostMapping(value = "/save-admin-user")
    @PreAuthorize("hasAuthority('SAVE_ADMIN_USER')")
    public ResponseEntity<String> saveAdminUser(@RequestBody UserDto userDto) {
        try {
            User savedUser = userService.saveAdminUser(userDto);
            logger.info("Admin user with email {} successfully saved.", userDto.getMail());
            return new ResponseEntity<>("User saved.", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving admin user with email {}: {}", userDto.getMail(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PutMapping(value = "/approve-registration-request/{id}")
//    @PreAuthorize("hasAuthority('APROVE_REGISTRATION_REQUEST')")
//    public ResponseEntity<String> register(@PathVariable  Long id){
//        ClientActivationToken token = clientService.approveRegistrationRequest(id);
//        Client client = clientService.findById(id);
//        try {
//            System.out.println("Thread id: " + Thread.currentThread().getId());
//            emailService.sendRegistrationApprovalAsync(client, token);
//        }catch( Exception e ){
//            logger.info("Greska prilikom slanja emaila: " + e.getMessage());
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PutMapping(value = "/approve-registration-request/{id}")
    @PreAuthorize("hasAuthority('APROVE_REGISTRATION_REQUEST')")
    public ResponseEntity<String> register(@PathVariable Long id) {
        try {
            ClientActivationToken token = clientService.approveRegistrationRequest(id);
            Client client = clientService.findById(id);

            try {
                emailService.sendRegistrationApprovalAsync(client, token);
                logger.info("Registration approval email sent successfully for client with ID {}", HashUtil.hash(String.valueOf(id)));
            } catch (Exception e) {
                logger.error("Error sending registration approval email for client with ID {}: {}", HashUtil.hash(String.valueOf(id)), e.getMessage());
                throw e;
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error approving registration request for client with ID {}: {}", HashUtil.hash(String.valueOf(id)), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @PutMapping(value = "/reject-registration-request/{id}/{reason}")
//    @PreAuthorize("hasAuthority('REJECT_REGISTRATION_REQUEST')")
//    public ResponseEntity<String> register(@PathVariable  Long id, @PathVariable String reason){
//        clientService.rejectRegistrationRequest(id, reason);
//        Client client = clientService.findById(id);
//        User user = client.getUser();
//        try {
//            System.out.println("Thread id: " + Thread.currentThread().getId());
//            emailService.sendRegistrationRejectionAsync(client, reason);
//            clientService.delete(client);
//            userService.delete(user);
//        }catch( Exception e ){
//            logger.info("Greska prilikom slanja emaila: " + e.getMessage());
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PutMapping(value = "/reject-registration-request/{id}/{reason}")
    @PreAuthorize("hasAuthority('REJECT_REGISTRATION_REQUEST')")
    public ResponseEntity<String> rejectRegistrationRequest(@PathVariable Long id, @PathVariable String reason) {
        try {
            clientService.rejectRegistrationRequest(id, reason);
            Client client = clientService.findById(id);
            User user = client.getUser();

            emailService.sendRegistrationRejectionAsync(client, reason);
            clientService.delete(client);
            userService.delete(user);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error rejecting registration request for client with ID {}: {}", HashUtil.hash(String.valueOf(id)), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // pristup: Administrator
//    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('GET_ALL_CLIENTS')")
//    public List<Client> getAllClients() {
//        return clientService.getAllClients();
//    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('GET_ALL_CLIENTS')")
    public List<Client> getAllClients() throws IllegalBlockSizeException, BadPaddingException {
        try {
            return clientService.getAllClients();
        } catch (Exception e) {
            logger.error("Error retriving all clients: {}", e.getMessage());
            throw e;
        }
    }


    @GetMapping("/byUserId/{userId}")
    @PreAuthorize("hasAuthority('GET_CLIENT_BYUSERID')")
    public ResponseEntity<ClientDto> getClientByUserId(@PathVariable Long userId) throws IllegalBlockSizeException, BadPaddingException {
        KeyStoreReader keyStoreReader = new KeyStoreReader();

        Client client = clientService.getClientByUserId(userId);
        String alias = client.getUser().getMail();

        SecretKey secretKey = keyStoreReader.readSecretKey(client.getUser().getMail()+".jks", alias, "marketing-agency".toCharArray(), "marketing-agency".toCharArray());

        if (client != null && secretKey != null) {
            ClientDto clientDto = new ClientDto(
                    client.getId(),
                    client.getUser().getMail(),
                    client.getType(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getCompanyName(),
                    client.getPib(),   //er
                    client.getClientPackage().getName(),
                    client.getPhoneNumber(secretKey),
                    client.getAddress(secretKey),
                    client.getCity(),
                    client.getCountry(),
                    client.getIsApproved()
            );
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    // pristup: Client
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_CLIENT')")
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto) throws IllegalBlockSizeException, BadPaddingException {
        KeyStoreReader keyStoreReader = new KeyStoreReader();

        String mail = clientDto.getUser();
        User user = userService.findByMail(mail);

        String packageName = clientDto.getClientPackage();
        Package pack = packageService.findByName(packageName);

        String alias = clientDto.getUser();
        SecretKey secretKey = keyStoreReader.readSecretKey(clientDto.getUser() + ".jks", alias, "marketing-agency".toCharArray(), "marketing-agency".toCharArray());

        if (secretKey == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Client updatedClient = new Client(
                clientDto.getId(),
                user,
                clientDto.getType(),
                clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getCompanyName(),
                clientDto.getPib(),
                pack,
                clientDto.getPhoneNumber(),
                clientDto.getAddress(),
                clientDto.getCity(),
                clientDto.getCountry(),
                clientDto.getIsApproved()
        );

        Client updated = clientService.updateClient(updatedClient, secretKey);

        if (updated != null) {
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
        ///i ovooo!!!!
//    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('UPDATE_CLIENT')")
//    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto) {
//        try {
//            String mail = clientDto.getUser();
//            User user = userService.findByMail(mail);
//
//            String packageName = clientDto.getClientPackage();
//            Package pack = packageService.findByName(packageName);
//
//            Client updatedClient = new Client(
//                    clientDto.getId(),
//                    user,
//                    clientDto.getType(),
//                    clientDto.getFirstName(),
//                    clientDto.getLastName(),
//                    clientDto.getCompanyName(),
//                    clientDto.getPib(),
//                    pack,
//                    clientDto.getPhoneNumber(),
//                    clientDto.getAddress(),
//                    clientDto.getCity(),
//                    clientDto.getCountry(),
//                    clientDto.getIsApproved()
//            );
//
//            Client updated = clientService.updateClient(updatedClient);
//
//            if (updated != null) {
//                logger.info("Client {} successfully updated.", updated.getUser());
//                return new ResponseEntity<>(clientDto, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            logger.error("Error updating client {} : {}", clientDto.getUser(),e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
