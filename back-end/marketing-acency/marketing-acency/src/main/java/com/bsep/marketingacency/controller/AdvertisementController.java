package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.service.AdvertisementService;
import com.bsep.marketingacency.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/advertisement")
public class AdvertisementController {
    private Logger logger =  LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/pending")
    public List<Advertisement> getPendingAdvertisements() {
        return advertisementService.getPendingAdvertisements();
    }

    @GetMapping("/accepted")
    public List<Advertisement> getAcceptedAdvertisements() {
        return advertisementService.getAcceptedAdvertisements();
    }
}
