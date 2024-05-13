package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.AdvertisementDto;
import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.service.AdvertisementService;
import com.bsep.marketingacency.service.ClientService;
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
    private Logger logger =  LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private ClientService clientService;

    // pristup: Employee
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('GET_PENDING_ADVERTISMENTS')")
    public List<Advertisement> getPendingAdvertisements() {
        return advertisementService.getPendingAdvertisements();
    }

    // pristup: Employee
    @GetMapping("/accepted")
    @PreAuthorize("hasAuthority('GET_ACCEPTED_ADVERTISMENTS')")
    public List<Advertisement> getAcceptedAdvertisements() {
        return advertisementService.getAcceptedAdvertisements();
    }

    // pristup: Employee, Client
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_ADVERTISMENT')")
    public ResponseEntity<AdvertisementDto> updateAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
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

        // Call the service method to update the employee
        Advertisement updated = advertisementService.updateAdvertisement(advertisementDto);

        if (updated != null) {
            return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // pristup: Client
    @GetMapping("/byClientUserId/{clientUserId}")
    @PreAuthorize("hasAuthority('GET__ADVERTISMENTS_BY_CLIENT')")
    public List<Advertisement> getAdvertisementsByClientUserId(@PathVariable Long clientUserId) {
        return advertisementService.getAdvertisementsByClientUserId(clientUserId);
    }

    // pristup: Client
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('CREATE_ADVERTISMENT')")
    public ResponseEntity<String> createAdvertisement(@RequestBody AdvertisementDto advertisementDto) {

        Advertisement savedAdvertisement = advertisementService.saveAdvertisement(advertisementDto);

        return new ResponseEntity<>("Employee created.",HttpStatus.CREATED);
    }

    @GetMapping("/getClientIdByAdvertismentId/{requestId}")
    @PreAuthorize("hasAuthority('GET_REQUEST_ID')")
    public ResponseEntity<Long> getClientIdByAdvertismentId(@PathVariable Long  requestId){
      Long id =  advertisementService.getClientIdByAdvertismentId(requestId);

        return new ResponseEntity<>(id,HttpStatus.OK);
    }

}
