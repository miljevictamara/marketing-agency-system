package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.AdministratorDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.model.Administrator;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.service.AdministratorService;
import com.bsep.marketingacency.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/administrator")
public class AdministratorController {
    private Logger logger =  LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<AdministratorDto> getAdministratorByUserId(@PathVariable Long userId) {
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
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
}
