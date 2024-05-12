package com.bsep.marketingacency.service;

import com.bsep.marketingacency.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bsep.marketingacency.model.Package;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    public Package findByName(String name) {
        return packageRepository.findByName(name);
    }
}
