package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.AdministratorDto;
import com.bsep.marketingacency.model.Administrator;
import com.bsep.marketingacency.service.AdministratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/administrator")
public class AdministratorController {
    private Logger logger =  LoggerFactory.getLogger(ClientController.class);

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
            Administrator administrator = administratorService.getAdministratorByUserId(userId);
            if (administrator != null) {
                AdministratorDto administratorDto = new AdministratorDto(
                        administrator.getId(),
                        administrator.getFirstName(),
                        administrator.getLastName(),
                        administrator.getAddress(),
                        administrator.getCity(),
                        administrator.getCountry(),
                        administrator.getPhoneNumber(),
                        administrator.getUser()
                );
                return new ResponseEntity<>(administratorDto, HttpStatus.OK);
            } else {
                logger.warn("Administrator with user ID {} not found.", userId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while fetching administrator by user ID {}: {}", userId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // pristup: Administrator

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<AdministratorDto> updateAdministrator(@RequestBody AdministratorDto administratorDto) {
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

        Administrator updated = administratorService.updateAdministrator(updatedAdministrator);

        if (updated != null) {
            return new ResponseEntity<>(administratorDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // pristup: Administrator

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Map<String, String>> createAdministrator(@RequestBody AdministratorDto administratorDto) {
        Administrator savedAdministrator = administratorService.saveAdministrator(administratorDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Administrator created.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
