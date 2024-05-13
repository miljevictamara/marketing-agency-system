package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.AdvertisementDto;
import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.service.AdvertisementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/advertisement")
public class AdvertisementController {
    private Logger logger =  LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AdvertisementService advertisementService;

    // pristup: Employee
    @GetMapping("/pending")
    public List<Advertisement> getPendingAdvertisements() {
        return advertisementService.getPendingAdvertisements();
    }

    // pristup: Employee
    @GetMapping("/accepted")
    public List<Advertisement> getAcceptedAdvertisements() {
        return advertisementService.getAcceptedAdvertisements();
    }

    // pristup: Employee, Client
    @PutMapping("/update")
    public ResponseEntity<AdvertisementDto> updateAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
        // Convert EmployeeDto to Employee object
        Advertisement updatedAdvertisement = new Advertisement(
                advertisementDto.getId(),
                advertisementDto.getClient(),
                advertisementDto.getSlogan(),
                advertisementDto.getDuration(),
                advertisementDto.getDescription(),
                advertisementDto.getDeadline(),
                advertisementDto.getActive_from(),
                advertisementDto.getActive_to(),
                advertisementDto.getRequest_description(),
                advertisementDto.getStatus()
        );

        // Call the service method to update the employee
        Advertisement updated = advertisementService.updateAdvertisement(updatedAdvertisement);

        if (updated != null) {
            return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // pristup: Client
    @GetMapping("/byClientUserId/{clientUserId}")
    public List<Advertisement> getAdvertisementsByClientUserId(@PathVariable Long clientUserId) {
        return advertisementService.getAdvertisementsByClientUserId(clientUserId);
    }

    // pristup: Client
    @PostMapping(value = "/create")
    public ResponseEntity<String> createAdvertisement(@RequestBody AdvertisementDto advertisementDto) {

        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisementDto);

        return new ResponseEntity<>("Employee created.",HttpStatus.CREATED);
    }
}
