package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.AdvertisementDto;
import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.service.AdvertisementService;
import com.bsep.marketingacency.service.ClientService;
import com.bsep.marketingacency.util.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/advertisement")
public class AdvertisementController {
    private Logger logger =  LoggerFactory.getLogger(AdministratorController.class);

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private ClientService clientService;

    // pristup: Employee
//    @GetMapping("/pending")
//    @PreAuthorize("hasAuthority('GET_PENDING_ADVERTISMENTS')")
//    public List<Advertisement> getPendingAdvertisements() {
//        return advertisementService.getPendingAdvertisements();
//    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('GET_PENDING_ADVERTISMENTS')")
    public List<Advertisement> getPendingAdvertisements() {
        try {
            return advertisementService.getPendingAdvertisements();
        } catch (Exception e) {
            logger.error("Error retrieving pending advertisements: {}", e.getMessage());
            throw e;
        }
    }


    // pristup: Employee
//    @GetMapping("/accepted")
//    @PreAuthorize("hasAuthority('GET_ACCEPTED_ADVERTISMENTS')")
//    public List<Advertisement> getAcceptedAdvertisements() {
//        return advertisementService.getAcceptedAdvertisements();
//    }

    @GetMapping("/accepted")
    @PreAuthorize("hasAuthority('GET_ACCEPTED_ADVERTISMENTS')")
    public List<Advertisement> getAcceptedAdvertisements() {
        try {
            return advertisementService.getAcceptedAdvertisements();
        } catch (Exception e) {
            logger.error("Error retrieving accepted advertisements: {}", e.getMessage());
            throw e;
        }
    }


    // pristup: Employee, Client
//    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('UPDATE_ADVERTISMENT')")
//    public ResponseEntity<AdvertisementDto> updateAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
//        // Convert EmployeeDto to Employee object
//        Long id = advertisementDto.getClientId();
//        Client client = clientService.findById(id);
//
//        Advertisement updatedAdvertisement = new Advertisement(
//                advertisementDto.getId(),
//                client,
//                advertisementDto.getSlogan(),
//                advertisementDto.getDuration(),
//                advertisementDto.getDescription(),
//                advertisementDto.getDeadline(),
//                advertisementDto.getActive_from(),
//                advertisementDto.getActive_to(),
//                advertisementDto.getRequest_description(),
//                advertisementDto.getStatus()
//        );
//
//        // Call the service method to update the employee
//        Advertisement updated = advertisementService.updateAdvertisement(advertisementDto);
//
//        if (updated != null) {
//            return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_ADVERTISMENT')")
    public ResponseEntity<AdvertisementDto> updateAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
        try {
            // Convert EmployeeDto to Employee object
            Long id = advertisementDto.getClientId();
            Client client = clientService.findById(id);

            Advertisement updatedAdvertisement = new Advertisement(
                    advertisementDto.getId(),
                    client,
                    advertisementDto.getSlogan(),
                    advertisementDto.getDuration(),
                    advertisementDto.getDescription(),
                    advertisementDto.getDeadline(),
                    advertisementDto.getActive_from(),
                    advertisementDto.getActive_to(),
                    advertisementDto.getRequest_description(),
                    advertisementDto.getStatus()
            );

            // Call the service method to update the advertisement
            Advertisement updated = advertisementService.updateAdvertisement(advertisementDto);

            if (updated != null) {
                logger.info("Advertisement {} updated successfully.", HashUtil.hash(advertisementDto.getId().toString()));
                return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
            } else {
                logger.error("Advertisement update failed: Advertisement with ID {} not found.", HashUtil.hash(advertisementDto.getId().toString()));
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating advertisement {} : {}", HashUtil.hash(advertisementDto.getId().toString()), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // pristup: Client
//    @GetMapping("/byClientUserId/{clientUserId}")
//    @PreAuthorize("hasAuthority('GET__ADVERTISMENTS_BY_CLIENT')")
//    public List<Advertisement> getAdvertisementsByClientUserId(@PathVariable Long clientUserId) {
//        return advertisementService.getAdvertisementsByClientUserId(clientUserId);
//    }

    @GetMapping("/byClientUserId/{clientUserId}")
    @PreAuthorize("hasAuthority('GET__ADVERTISMENTS_BY_CLIENT')")
    public List<Advertisement> getAdvertisementsByClientUserId(@PathVariable Long clientUserId) {
        try {
            return advertisementService.getAdvertisementsByClientUserId(clientUserId);
        } catch (Exception e) {
            logger.error("Error fetching advertisements for client user ID {}: {}", HashUtil.hash(clientUserId.toString()), e.getMessage());
            throw e;
        }
    }


    // pristup: Client
//    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('CREATE_ADVERTISMENT')")
//    public ResponseEntity<String> createAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
//
//        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisementDto);
//
//        return new ResponseEntity<>("Employee created.",HttpStatus.CREATED);
//    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('CREATE_ADVERTISMENT')")
    public ResponseEntity<String> createAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
        try {
            Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisementDto);
            logger.info("Advertisement created successfully with ID: {}", HashUtil.hash(savedAdvertisement.getId().toString()));
            return new ResponseEntity<>("Advertisement created.", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating advertisement {}: {}", HashUtil.hash(advertisementDto.getId().toString()),e.getMessage());
            return new ResponseEntity<>("Error creating advertisement.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/getClientIdByAdvertismentId/{requestId}")
//    @PreAuthorize("hasAuthority('GET_REQUEST_ID')")
//    public ResponseEntity<Long> getClientIdByAdvertismentId(@PathVariable Long  requestId){
//      Long id =  advertisementService.getClientIdByAdvertismentId(requestId);
//      return new ResponseEntity<>(id,HttpStatus.OK);
//    }

    @GetMapping("/getClientIdByAdvertismentId/{requestId}")
    @PreAuthorize("hasAuthority('GET_REQUEST_ID')")
    public ResponseEntity<Long> getClientIdByAdvertismentId(@PathVariable Long requestId){
        try {
            Long id =  advertisementService.getClientIdByAdvertismentId(requestId);
            return new ResponseEntity<>(id,HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching client ID for advertisement ID {}: {}", HashUtil.hash(requestId.toString()), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
