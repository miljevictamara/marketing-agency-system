package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.AdministratorDto;
import com.bsep.marketingacency.keystores.KeyStoreReader;
import com.bsep.marketingacency.keystores.KeyStoreWriter;
import com.bsep.marketingacency.model.Administrator;
import com.bsep.marketingacency.service.AdministratorService;
import com.bsep.marketingacency.util.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/administrator")
public class AdministratorController {
    private Logger logger =  LoggerFactory.getLogger(AdministratorController.class);

    @Autowired
    private AdministratorService administratorService;

    // pristup: Administrator
//    @GetMapping("/byUserId/{userId}")
//    @PreAuthorize("hasAuthority('GET_BYUSERID')")
//    public ResponseEntity<AdministratorDto> getAdministratorByUserId(@PathVariable Long userId) {
//        Administrator administrator = administratorService.getAdministratorByUserId(userId);
//        if (administrator != null) {
//            AdministratorDto administratorDto = new AdministratorDto(
//                    administrator.getId(),
//                    administrator.getFirstName(),
//                    administrator.getLastName(),
//                    administrator.getAddress(),
//                    administrator.getCity(),
//                    administrator.getCountry(),
//                    administrator.getPhoneNumber(),
//                    administrator.getUser()
//            );
//            return new ResponseEntity<>(administratorDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/byUserId/{userId}")
    @PreAuthorize("hasAuthority('GET_BYUSERID')")
    public ResponseEntity<AdministratorDto> getAdministratorByUserId(@PathVariable Long userId) {
        try {
            logger.info("Trying to retrieve administrator information by userId {}.", HashUtil.hash(userId.toString()));
            KeyStoreReader keyStoreReader = new KeyStoreReader();

            Administrator administrator = administratorService.getAdministratorByUserId(userId);
            String alias = administrator.getUser().getMail();

            SecretKey secretKey = keyStoreReader.readSecretKey(administrator.getUser().getMail()+".jks", alias, "marketing-agency".toCharArray(), "marketing-agency".toCharArray());

            if (administrator != null) {
                AdministratorDto administratorDto = new AdministratorDto(
                        administrator.getId(),
                        administrator.getFirstName(),
                        administrator.getLastName(),
                        administrator.getAddress(secretKey),
                        administrator.getCity(),
                        administrator.getCountry(),
                        administrator.getPhoneNumber(secretKey),
                        administrator.getUser()
                );
                logger.info("Administrator {} information successfully retrieved.", administratorDto.getUser().getMail());
                return new ResponseEntity<>(administratorDto, HttpStatus.OK);
            } else {
                logger.warn("Administrator with user ID {} not found.", HashUtil.hash(userId.toString()));
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while fetching administrator by user ID {}.", HashUtil.hash(userId.toString()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // pristup: Administrator

//    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
//    public ResponseEntity<AdministratorDto> updateAdministrator(@RequestBody AdministratorDto administratorDto) {
//        Administrator updatedAdministrator = new Administrator(
//                administratorDto.getId(),
//                administratorDto.getFirstName(),
//                administratorDto.getLastName(),
//                administratorDto.getAddress(),
//                administratorDto.getCity(),
//                administratorDto.getCountry(),
//                administratorDto.getPhoneNumber(),
//                administratorDto.getUser()
//        );
//
//        Administrator updated = administratorService.updateAdministrator(updatedAdministrator);
//
//        if (updated != null) {
//            return new ResponseEntity<>(administratorDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<AdministratorDto> updateAdministrator(@RequestBody AdministratorDto administratorDto) {
        try {
            logger.info("Trying to update administrator {}.", administratorDto.getUser().getMail());
            KeyStoreReader keyStoreReader = new KeyStoreReader();
            String alias = administratorDto.getUser().getMail();
            SecretKey secretKey = keyStoreReader.readSecretKey(administratorDto.getUser().getMail() + ".jks", alias, "marketing-agency".toCharArray(), "marketing-agency".toCharArray());

            Administrator updatedAdministrator = new Administrator(
                    administratorDto.getId(),
                    administratorDto.getFirstName(),
                    administratorDto.getLastName(),
                    administratorDto.getAddress(),
                    administratorDto.getCity(),
                    administratorDto.getCountry(),
                    administratorDto.getPhoneNumber(),
                    administratorDto.getUser()
            );

            Administrator updated = administratorService.updateAdministrator(updatedAdministrator, secretKey);

            if (updated != null) {
                logger.info("Administrator {} successfully updated.", administratorDto.getUser().getMail());
                return new ResponseEntity<>(administratorDto, HttpStatus.OK);
            } else {
                logger.warn("Administrator {} not found for updating.", administratorDto.getUser().getMail());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while updating administrator {}.", administratorDto.getUser().getMail());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // pristup: Administrator

//    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
//    public ResponseEntity<Map<String, String>> createAdministrator(@RequestBody AdministratorDto administratorDto) {
//        Administrator savedAdministrator = administratorService.saveAdministrator(administratorDto);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Administrator created.");
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Map<String, String>> createAdministrator(@RequestBody AdministratorDto administratorDto) {
        try {
            logger.info("Trying to create administrator {}.", administratorDto.getUser().getMail());
            SecretKey secretKey = KeyStoreWriter.generateSecretKey("AES", 256);
            KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
            keyStoreWriter.loadKeyStore(null, "marketing-agency".toCharArray());
            keyStoreWriter.writeSecretKey(administratorDto.getUser().getMail(), secretKey, "marketing-agency".toCharArray());
            keyStoreWriter.saveKeyStore(administratorDto.getUser().getMail() + ".jks", "marketing-agency".toCharArray());

            Administrator savedAdministrator = administratorService.saveAdministrator(administratorDto, secretKey);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Administrator created.");
            logger.info("Administrator {} successfully created.", savedAdministrator.getUser().getMail());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while creating administrator {}.", administratorDto.getUser().getMail());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
