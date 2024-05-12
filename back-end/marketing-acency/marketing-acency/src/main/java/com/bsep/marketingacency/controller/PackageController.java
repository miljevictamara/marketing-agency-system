package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.model.Package;
import com.bsep.marketingacency.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping(value = "/{name}")
    public ResponseEntity<Package> getPackageByName(@PathVariable String name) {
        Package packagee = packageService.findByName(name);
        if (packagee != null) {
            return new ResponseEntity<>(packagee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
